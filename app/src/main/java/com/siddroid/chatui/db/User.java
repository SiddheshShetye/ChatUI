package com.siddroid.chatui.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class User implements Parcelable{
    public static final int SENDER = 1,BOT = 2;

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "user_type")
    int userType;

    @ColumnInfo(name = "nickname")
    String nickname;

    public int getUserType() {
        return userType;
    }

    @Ignore
    public User(Parcel in){
        uid = in.readInt();
        userType = in.readInt();
        nickname = in.readString();
    }

    @Ignore
    public User(){}

    public User(int userType, String nickname) {
        this.userType = userType;
        this.nickname = nickname;
    }

    public int getUid() {
        return uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){

        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int i) {
            return new User[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(uid);
        parcel.writeInt(userType);
        parcel.writeString(nickname);
    }
}