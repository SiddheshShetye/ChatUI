package com.siddroid.chatui.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {User.class, Message.class}, version = 1)
public abstract class ChatDatabase extends RoomDatabase {
    public static final String DB_NAME = "chat.db";
    public abstract UserDAO userDao();
    public abstract MessageDAO messageDao();
}