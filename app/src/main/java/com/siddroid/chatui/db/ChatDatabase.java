package com.siddroid.chatui.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Message.class}, version = 2)
public abstract class ChatDatabase extends RoomDatabase {
    public static final String DB_NAME = "chat.db";
    public abstract MessageDAO messageDao();
}