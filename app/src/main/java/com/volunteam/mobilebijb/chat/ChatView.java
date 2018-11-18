package com.volunteam.mobilebijb.chat;

import com.volunteam.mobilebijb.chat.model.Chat;

import java.util.List;

public interface ChatView {
    void setChat(List<Chat> chatList);
    void onSuccessSendmessage(String message);
    void onFailedSendmessage(String message);
    void onCancelGetMessage(String message);
}
