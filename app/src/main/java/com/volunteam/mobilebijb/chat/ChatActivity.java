package com.volunteam.mobilebijb.chat;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.chat.adapter.ChatAdapter;
import com.volunteam.mobilebijb.chat.model.Chat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ChatActivity extends AppCompatActivity implements ChatView{
    private Toolbar toolbar;
    private SwipeRefreshLayout refresh_layout;
    private RecyclerView recycler_chat;
    private EditText edit_message;
    private ImageView btn_send;
    private ProgressDialog mProgressDialog;

    private final ChatPresenter chatPresenter = new ChatPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        defineViews();
        setToolbar(toolbar);
        changeButtonSendBackground(edit_message);
        showProgressDialog();
        chatPresenter.getChat();
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                chatPresenter.getChat();
            }
        });
    }

    private void defineViews(){
        toolbar = findViewById(R.id.toolbar);
        refresh_layout = findViewById(R.id.refresh_layout);
        recycler_chat = findViewById(R.id.recycler_chat);
        edit_message = findViewById(R.id.edit_message);
        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnSend();
            }
        });
    }

    private void setToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Customer Service");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void changeButtonSendBackground(final EditText edit_message){
        edit_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_message.getText().equals("") || edit_message.getText().equals(" ") || edit_message.getText().toString().replace(" ","").equals("")){
                    btn_send.setImageDrawable(getResources().getDrawable(R.drawable.btn_send_gray));
                    btn_send.setClickable(false);
                }else {
                    btn_send.setImageDrawable(getResources().getDrawable(R.drawable.btn_send_message));
                    btn_send.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onClickBtnSend(){
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = edit_message.getText().toString();

                if(!message.isEmpty()){
                    /*System.out.println("waktu sekarang: "+getTime());*/
                    sendMessageToFirebase();
                }
            }
        });
    }

    private void sendMessageToFirebase(){
        Chat chat = new Chat(getTime(), edit_message.getText().toString(), "http://www.rff.org/files/profile_pictures/Blonz_5x7.jpg","user");
        chatPresenter.sendChat(chat);
    }

    private void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void showProgressDialog() {
        dismissProgressDialog();
        mProgressDialog = ProgressDialog.show(this, "", "Loading...");
    }

    private void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    private String getTime() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));
        Date date = c.getTime();
        /*SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");*/
        String strDate = String.valueOf(date);
        return strDate;
    }

    @Override
    public void setChat(List<Chat> chatList) {
        ChatAdapter adapter = new ChatAdapter(chatList);
        recycler_chat.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        recycler_chat.setLayoutManager(mLinearLayoutManager);
        recycler_chat.setAdapter(adapter);
        refresh_layout.setRefreshing(false);
        dismissProgressDialog();
    }

    @Override
    public void onSuccessSendmessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        showProgressDialog();
        chatPresenter.getChat();
        hideSoftKeyboard();
        edit_message.setText("");
    }

    @Override
    public void onFailedSendmessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelGetMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
