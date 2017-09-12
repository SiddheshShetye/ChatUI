package com.siddroid.chatui.bot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class BotMessageInsertedBroadcastReceiver extends BroadcastReceiver {

    private final Handler handler;
    public static final String ACTION = "BOT.MESSAGE>RECEIVED";

    public BotMessageInsertedBroadcastReceiver(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Message msg = handler.obtainMessage();
        msg.obj = intent;
        handler.sendMessage(msg);
    }
}
