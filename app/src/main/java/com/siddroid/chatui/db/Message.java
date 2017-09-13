package com.siddroid.chatui.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class Message implements Parcelable{

    public static final int SENDER = 1,BOT = 2;
    private static final String BOT_NAME = "SID";

    @PrimaryKey(autoGenerate = true)
    private int mid;

    @ColumnInfo(name = "name")
    private String message;

    @ColumnInfo(name = "created_at")
    private String createdAt;

    @ColumnInfo(name = "message_type")
    private int messageType;

    @Ignore
    private String nickname;

    @Ignore
    private Message(Parcel in){
        mid = in.readInt();
        message = in.readString();
        createdAt = in.readString();
        messageType = in.readInt();
        nickname = in.readString();
    }

    public Message(String message, String createdAt, int messageType) {
        this.message = message;
        this.createdAt = createdAt;
        this.messageType = messageType;
        this.nickname = messageType == Message.BOT ? Message.BOT_NAME : "me";
    }

    @Ignore
    public Message(){}

    public int getMid() {
        return mid;
    }

    public String getMessage() {
        return message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNickname() {
        return nickname;
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
        parcel.writeString(createdAt);
        parcel.writeInt(messageType);
        parcel.writeString(nickname);
    }
}