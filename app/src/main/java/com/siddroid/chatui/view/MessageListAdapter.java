package com.siddroid.chatui.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.siddroid.chatui.R;
import com.siddroid.chatui.databinding.ItemMessageReceivedBinding;
import com.siddroid.chatui.databinding.ItemMessageSentBinding;
import com.siddroid.chatui.db.Message;

import java.util.List;

class MessageListAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private List<Message> messageList;

    MessageListAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);

        if (message.getMessageType() == Message.SENDER) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            ItemMessageSentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(binding);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            ItemMessageReceivedBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(binding);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        private ItemMessageSentBinding binding;

        SentMessageHolder(ItemMessageSentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Message message) {
            binding.setMessage(message);
            binding.executePendingBindings();
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        private ItemMessageReceivedBinding binding;

        ReceivedMessageHolder(ItemMessageReceivedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Message message) {
            binding.setMessage(message);
            binding.executePendingBindings();
        }
    }
}