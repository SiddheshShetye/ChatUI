package com.siddroid.chatui.presenter;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.siddroid.chatui.ChatApplication;
import com.siddroid.chatui.db.Message;

import java.util.Collections;
import java.util.List;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View view;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
    }


    @Override
    public void loadPreviousMessages() {
        view.showListLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Message> messageList = ChatApplication.getInstance().getDatabase().messageDao().getAll();
                Collections.reverse(messageList);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        view.hideListLoading();
                        view.showPreviousMessages(messageList);
                    }
                });
            }
        }).start();
    }

    private void sendNewMessage(String message, int userType) {
        final Message senderMessage = new Message(message, com.siddroid.chatui.util.Util.formatDateTime(System.currentTimeMillis()), userType);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(senderMessage.getMessageType() == Message.BOT){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ChatApplication.getInstance().getDatabase().messageDao().insert(senderMessage);
            }
        }).start();
        int wait = 0;
        if (senderMessage.getMessageType() == Message.BOT){
            wait = 1000;
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                view.updateMessage(senderMessage);
            }
        },wait);
    }

    @Override
    public void sendClicked(String message) {
        if (TextUtils.isEmpty(message)){
            view.showError();
            return;
        }
        sendNewMessage(message,Message.SENDER);
        sendNewMessage("I am a BOT, I don't understand : "+message,Message.BOT);
    }
}
