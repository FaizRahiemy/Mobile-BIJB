package com.volunteam.mobilebijb.chat.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.chat.model.Chat;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    private List<Chat> chatList = new ArrayList<>();
    public static final int ITEM_TYPE_SENT = 0;
    public static final int ITEM_TYPE_RECEIVED = 1;

    public ChatAdapter(List<Chat> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = null;
        if (i == ITEM_TYPE_SENT) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_sender, null);
        } else if (i == ITEM_TYPE_RECEIVED) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_recipient, null);
        }
        return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        if (chatList.get(position).getName().equals("user")) {
            return ITEM_TYPE_SENT;
        } else {
            return ITEM_TYPE_RECEIVED;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setMessage(chatList.get(i));
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_time_sent;
        private TextView chatMsgTextView;
        private CircleImageView img_sender_chat;
        private TextView chatNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_time_sent = itemView.findViewById(R.id.txt_time_sent);
            chatMsgTextView = itemView.findViewById(R.id.chatMsgTextView);
            img_sender_chat = itemView.findViewById(R.id.img_sender_chat);
            chatNameTextView = itemView.findViewById(R.id.chatNameTextView);
        }

        public void setMessage(Chat chat){
            txt_time_sent.setText(chat.getTime());
            chatMsgTextView.setText(chat.getMessage());
            chatNameTextView.setText(chat.getName());
            Glide.with(this.itemView).load(chat.getImage()).into(img_sender_chat);
        }
    }
}
