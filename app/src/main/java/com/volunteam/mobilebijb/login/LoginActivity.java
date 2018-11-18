package com.volunteam.mobilebijb.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.hash.Hashing;
import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;
import com.volunteam.mobilebijb.config.TinyDB;
import com.volunteam.mobilebijb.home.HomeActivity;
import com.volunteam.mobilebijb.register.RegisterActivity;

import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnLogin;
    private EditText edtEmail, edtPassword;
    private TextView txRegister, txError, txLupaPass;
    private ProgressBar progressBar;
    private TinyDB tinyDB;
    private String token = "";
    private String id = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // // get id and token from sharedpreference
        tinyDB = new TinyDB(this);
        try {
            token = tinyDB.getString("token");
            id = tinyDB.getString("id");
            if (!token.equals("")&&!id.equals("")){
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }catch (Exception e){

        }

        //init views
        defineViews();
    }

    private void defineViews(){
        btnLogin = (Button)findViewById(R.id.btn_signin);
        edtEmail = (EditText)findViewById(R.id.edt_email_login);
        edtPassword = (EditText)findViewById(R.id.edt_password_login);
        txError = (TextView)findViewById(R.id.tx_error_login);
        txRegister = (TextView)findViewById(R.id.tx_register);
        txLupaPass = (TextView)findViewById(R.id.tx_lupapassword);
        progressBar = (ProgressBar)findViewById(R.id.pb_loading);
        btnLogin.setOnClickListener(this);
        txRegister.setOnClickListener(this);
        txLupaPass.setOnClickListener(this);

        //error gone on init
        txError.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signin:
                showProgressBar();
                if (isEmpty(edtEmail)||isEmpty(edtPassword)){
                    // field empty
                    setErrorMessage("Email or password cannot be empty");
                    hideProgressBar();
                }else{
                    // try login
                    txError.setVisibility(View.GONE);
                    login();
                }
                break;
            case R.id.tx_register:
                txError.setVisibility(View.GONE);
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
                break;
            case R.id.tx_lupapassword:
                // TODO navigate to lupa password page
                txError.setVisibility(View.GONE);
                Toast.makeText(this, "Lupa Password", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void setErrorMessage(String message){
        txError.setVisibility(View.VISIBLE);
        txError.setText(message);
    }

    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void login(){
        String passwordHash = Hashing.sha256()
                .hashString(edtPassword.getText().toString(), StandardCharsets.UTF_8)
                .toString();
        API api = MainAPIHelper.getClient("").create(API.class);
        Call<LoginResponse> call = api.login(edtEmail.getText().toString(), passwordHash);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                try{
                    if (response.body().getStatus_code()== 1) {
                        // valid
                        // save id and token to sharedpreference
                        tinyDB.putString("id",response.body().getResult_login().getId());
                        tinyDB.putString("token",response.body().getResult_login().getToken());
                        hideProgressBar();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        // not valid
                        setErrorMessage("The Email or password is incorrect");
                        hideProgressBar();
                    }
                }catch (Exception e){
                    hideProgressBar();
                    System.out.println("error"+e.getMessage());
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    hideProgressBar();
                    Toast.makeText(LoginActivity.this, "Error, please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
