package com.siddroid.chatui;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.siddroid.chatui.db.ChatDatabase;

public class ChatApplication extends Application {

    private static ChatApplication APP;
    private ChatDatabase database;

    public static ChatApplication getInstance(){
        return APP;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        APP = this;
        database = Room.databaseBuilder(getApplicationContext(), ChatDatabase.class, ChatDatabase.DB_NAME)
                .build();
    }

    public ChatDatabase getDatabase(){
        return database;
    }
}
