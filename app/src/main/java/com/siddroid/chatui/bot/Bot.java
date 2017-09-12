package com.siddroid.chatui.bot;

import android.content.Context;
import android.content.Intent;

import com.siddroid.chatui.ChatApplication;
import com.siddroid.chatui.db.ChatDatabase;
import com.siddroid.chatui.db.Message;
import com.siddroid.chatui.db.User;

public class Bot {

    private final ChatDatabase database;
    private final Context mContext;
    private User botUser;
    private static Bot bot;

    public static Bot getInstance(Context context) {
        if (bot == null){
            synchronized (Bot.class){
                if (bot == null){
                    bot = new Bot(context);
                }
            }
        }
        return bot;
    }

    private Bot(Context context) {
        mContext = context;
        database = ChatApplication.getInstance().getDatabase();
        initBotUser();
    }

    private void initBotUser(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                botUser = database.userDao().findByUserType(User.BOT);
                if (botUser == null){
                    botUser = new User(User.BOT,"Bot : Sid");
                    database.userDao().insert(botUser);

                }
            }
        }).start();
    }

    public  User getBotUser(){
        return botUser;
    }

    public void receiveFromBot(String userMessage){
        final Message botMssage = new Message("I am a bot and i dont understand : "+userMessage,System.currentTimeMillis(),botUser.getUserType());
        final Intent intent = new Intent(BotMessageInsertedBroadcastReceiver.ACTION);
        intent.putExtra("user",botUser);
        intent.putExtra("message",botMssage);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                database.messageDao().insert(botMssage);
                mContext.sendBroadcast(intent);
            }
        }).start();
    }
}
