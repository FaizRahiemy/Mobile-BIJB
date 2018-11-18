package com.volunteam.mobilebijb.chat;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.volunteam.mobilebijb.chat.model.Chat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ChatPresenter {
    private ChatView chatView;
    private final DatabaseReference firebaseMessageRef = FirebaseDatabase.getInstance().getReference().child("Messages");
    private final List<Chat> chatList = new ArrayList<>();

    public ChatPresenter(ChatView chatView) {
        this.chatView = chatView;
    }

    public void getChat(){
        chatList.clear();
        firebaseMessageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    Chat chat = snap.getValue(Chat.class);
                    if (!chat.getTime().equals("today")){
                        chat.setTime(timeConverter(chat.getTime()));
                        chatList.add(chat);
                    }else {
                        chatList.add(chat);
                    }
                }
                chatView.setChat(chatList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                chatView.onCancelGetMessage(databaseError.getMessage());
            }
        });
    }

    public void sendChat(Chat chat){
        firebaseMessageRef.push().setValue(chat).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    chatView.onSuccessSendmessage("pesan berhasil dikirim.");
                }else {
                    chatView.onFailedSendmessage("pesan gagal dikirim");
                }
            }
        });
    }

    private String timeConverter(String timeSent){
        String year = null, month= null, day= null, yearNow= null, monthNow= null, dayNow= null, time= null, timeNow;

        String timeFix;

        Date dateNow = getTime();

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        try{
            year = yearFormat.format(Date.parse(timeSent));
            month = monthFormat.format(Date.parse(timeSent));
            day = dayFormat.format(Date.parse(timeSent));
            time = timeFormat.format(Date.parse(timeSent));

            yearNow = yearFormat.format(dateNow);
            monthNow = monthFormat.format(dateNow);
            dayNow = dayFormat.format(dateNow);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (yearNow.equals(year)){
            if (monthNow.equals(month)){
                if (dayNow.equals(day)){
                    timeFix = (time);
                }else if (Integer.parseInt(dayNow) - Integer.parseInt(day) == 1){
                    timeFix = ("kemarin, "+time);
                }else {
                    SimpleDateFormat monthFormatDisplay = new SimpleDateFormat("MMM dd");
                    month = monthFormatDisplay.format(Date.parse(timeSent));
                    timeFix = (month+", "+time);
                }
            }else {
                SimpleDateFormat monthFormatDisplay = new SimpleDateFormat("MMM dd");
                month = monthFormatDisplay.format(Date.parse(timeSent));
                timeFix = (month+", "+time);
            }
        }else {
            SimpleDateFormat monthFormatDisplay = new SimpleDateFormat("MMM dd yyyy");
            month = monthFormatDisplay.format(Date.parse(timeSent));
            timeFix = (month+", "+time);
        }
        return timeFix;
    }

    private Date getTime() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));
        Date date = c.getTime();
        /*SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");*/
        /*String strDate = df.format(date);*/
        return date;
    }
}
