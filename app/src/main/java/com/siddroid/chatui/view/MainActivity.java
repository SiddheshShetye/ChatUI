package com.siddroid.chatui.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.siddroid.chatui.R;
import com.siddroid.chatui.databinding.ActivityMainBinding;
import com.siddroid.chatui.db.Message;
import com.siddroid.chatui.presenter.MainActivityContract;
import com.siddroid.chatui.presenter.MainActivityPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private MessageListAdapter messageAdapter;
    private List<Message> messageList = new ArrayList<>();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainActivityPresenter presenter = new MainActivityPresenter(this);
        binding.setPresenter(presenter);

        messageAdapter = new MessageListAdapter(messageList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        binding.rvMessageList.setLayoutManager(manager);
        binding.rvMessageList.setAdapter(messageAdapter);
        binding.rvMessageList.setItemAnimator(new DefaultItemAnimator());

        presenter.loadPreviousMessages();
    }

    //-------------View Methods--------------------
    @Override
    public void showPreviousMessages(List<Message> msgList) {
        messageList.addAll(msgList);
        messageAdapter.notifyDataSetChanged();
        binding.rvMessageList.scrollToPosition(0);
    }

    @Override
    public void updateMessage(final Message message) {
        messageList.add(0,message);
        messageAdapter.notifyItemInserted(0);
        binding.rvMessageList.scrollToPosition(0);
        binding.edtChat.setText("");
    }

    @Override
    public void showListLoading() {
        binding.progressBar.setVisibility(android.view.View.VISIBLE);
        binding.rvMessageList.setVisibility(android.view.View.INVISIBLE);
    }

    @Override
    public void hideListLoading() {
        binding.progressBar.setVisibility(android.view.View.INVISIBLE);
        binding.rvMessageList.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.error_message,Toast.LENGTH_LONG).show();
    }
    //---------------------------------------------
}
