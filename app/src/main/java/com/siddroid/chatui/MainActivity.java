package com.siddroid.chatui;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.siddroid.chatui.bot.Bot;
import com.siddroid.chatui.bot.BotMessageInsertedBroadcastReceiver;
import com.siddroid.chatui.db.ChatDatabase;
import com.siddroid.chatui.db.Message;
import com.siddroid.chatui.db.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private ChatDatabase database;
    private ImageButton btnSend;
    private EditText edtMessage;
    private Bot botUser;
    private BotMessageInsertedBroadcastReceiver receiver;

    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            Intent intent = (Intent) msg.obj;
            updateBotMessage(intent);
        }
    };
    private List<Message> messageList = new ArrayList<>();
    private ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = ChatApplication.getInstance().getDatabase();

        edtMessage = findViewById(R.id.edittext_chatbox);
        btnSend = findViewById(R.id.button_chatbox_send);
        mMessageRecycler = findViewById(R.id.reyclerview_message_list);
        progress = findViewById(R.id.progress_bar);
        progress.setVisibility(View.VISIBLE);
        mMessageRecycler.setVisibility(View.INVISIBLE);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Message senderMessage = new Message(edtMessage.getText().toString(),System.currentTimeMillis(), User.SENDER);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        database.messageDao().insert(senderMessage);
                    }
                }).start();
                messageList.add(0,senderMessage);
                mMessageAdapter.notifyDataSetChanged();
                mMessageRecycler.scrollToPosition(0);
                edtMessage.setText("");
                botUser.receiveFromBot(senderMessage.getMessage());
            }
        });


        mMessageAdapter = new MessageListAdapter(this, messageList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        mMessageRecycler.setLayoutManager(manager);
        mMessageRecycler.setAdapter(mMessageAdapter);
        botUser = Bot.getInstance(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                messageList.addAll(database.messageDao().getAll());
                Collections.reverse(messageList);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mMessageAdapter.notifyDataSetChanged();
                        mMessageRecycler.scrollToPosition(0);
                        progress.setVisibility(View.INVISIBLE);
                        mMessageRecycler.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
        receiver = new BotMessageInsertedBroadcastReceiver(handler);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver();
    }

    private void updateBotMessage(Intent intent){
        Message botMessage = intent.getParcelableExtra("message");
        messageList.add(0,botMessage);
        mMessageAdapter.notifyDataSetChanged();
        mMessageRecycler.scrollToPosition(0);
    }

    private void registerReceiver(){
        IntentFilter filter = new IntentFilter(BotMessageInsertedBroadcastReceiver.ACTION);
        registerReceiver(receiver,filter);
    }

    private void unregisterReceiver(){
        unregisterReceiver(receiver);
    }
}
