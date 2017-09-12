package com.siddroid.chatui.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user WHERE user_type = :type LIMIT 1")
    User findByUserType(int type);

    @Insert
    void insert(User user);
}
