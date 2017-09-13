package com.siddroid.chatui.presenter;

import com.siddroid.chatui.db.Message;

import java.util.List;

public class MainActivityContract {

    public interface View {
        void showPreviousMessages(List<Message> msgList);
        void updateMessage(Message message);
        void showListLoading();
        void hideListLoading();
        void showError();
    }

    interface Presenter {
        void loadPreviousMessages();
        void sendClicked(String message);
    }
}
