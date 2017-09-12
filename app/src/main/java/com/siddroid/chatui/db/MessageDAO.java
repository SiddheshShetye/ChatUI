package com.siddroid.chatui.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MessageDAO {

    @Query("SELECT * FROM Message")
    List<Message> getAll();

    @Insert
    void insert(Message message);
}
