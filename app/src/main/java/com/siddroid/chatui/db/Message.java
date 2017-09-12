package com.siddroid.chatui.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class Message implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int mid;

    @ColumnInfo(name = "name")
    private String message;

    @ColumnInfo(name = "created_at")
    private long createdAt;

    @ColumnInfo(name = "user_type")
    private int userType;

    @Ignore
    public Message(Parcel in){
        mid = in.readInt();
        message = in.readString();
        createdAt = in.readLong();
        userType = in.readInt();
    }

    public Message(String message, long createdAt, int userType) {
        this.message = message;
        this.createdAt = createdAt;
        this.userType = userType;
    }

    @Ignore
    public Message(){}

    public int getMid() {
        return mid;
    }

    public String getMessage() {
        return message;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public int getUserType() {
        return userType;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel parcel) {
            return new Message(parcel);
        }

        @Override
        public Message[] newArray(int i) {
            return new Message[i];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mid);
        parcel.writeString(message);
        parcel.writeLong(createdAt);
        parcel.writeInt(userType);
    }
}