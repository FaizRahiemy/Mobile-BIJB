package com.volunteam.mobilebijb.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
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
import com.volunteam.mobilebijb.login.LoginActivity;


import org.w3c.dom.Text;

import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnRegis;
    private EditText edtNama, edtEmail, edtPassword;
    private TextView txLogin, txLupaPass, txSuccess, txError;
    private TextInputLayout txInputNama, txInputEmail, txInputPass;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // init views
        defineViews();
    }

    private void defineViews(){
        btnRegis = (Button)findViewById(R.id.btn_signup);
        edtNama = (EditText)findViewById(R.id.edt_nama_regis);
        edtEmail = (EditText)findViewById(R.id.edt_email_regis);
        edtPassword = (EditText)findViewById(R.id.edt_password_regis);
        txLogin = (TextView)findViewById(R.id.tx_login);
        txLupaPass = (TextView)findViewById(R.id.tx_lupapassword);
        txInputNama = (TextInputLayout)findViewById(R.id.txinput_nama_regis);
        txInputEmail = (TextInputLayout)findViewById(R.id.txinput_email_regis);
        txInputPass = (TextInputLayout)findViewById(R.id.txinput_password_regis);
        progressBar = (ProgressBar)findViewById(R.id.pb_loading);
        txSuccess = (TextView)findViewById(R.id.tx_success_regis);
        txError = (TextView)findViewById(R.id.tx_error_regis);
        btnRegis.setOnClickListener(this);
        txLogin.setOnClickListener(this);
        txLupaPass.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signup:
                showProgressBar();

                // set error if empty
                if (isEmpty(edtNama)){
                    setFieldError(txInputNama, "Name cannot be empty");
                }else{
                    txInputNama.setErrorEnabled(false);
                }
                if (isEmpty(edtEmail)){
                    setFieldError(txInputEmail, "Email cannot be empty");
                }else{
                    txInputEmail.setErrorEnabled(false);
                }
                if (isEmpty(edtPassword)){
                    setFieldError(txInputPass, "Password cannot be empty");
                }else {
                    txInputPass.setErrorEnabled(false);
                }

                //register user if there is not an error
                if (!txInputNama.isErrorEnabled()&&!txInputEmail.isErrorEnabled()&&!txInputPass.isErrorEnabled()){
                    register();
                }else{
                    hideProgressBar();
                }
                break;
            case R.id.tx_login:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.tx_lupapassword:

                break;
        }
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void setFieldError(TextInputLayout txInput, String message){
        txInput.setErrorEnabled(true);
        txInput.setError(message);
    }

    private void setSuccessMessage(String message){
        txError.setVisibility(View.GONE);
        txSuccess.setText(message);
        txSuccess.setVisibility(View.VISIBLE);
    }

    private void setErrorMessage(String message){
        txSuccess.setVisibility(View.GONE);
        txError.setText(message);
        txError.setVisibility(View.VISIBLE);
    }

    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void register(){
        String passwordHash = Hashing.sha256()
                .hashString(edtPassword.getText().toString(), StandardCharsets.UTF_8)
                .toString();
        API api = MainAPIHelper.getClient("").create(API.class);
        Call<InsertResponse> call = api.register(edtNama.getText().toString(),
                edtEmail.getText().toString(), passwordHash);
        call.enqueue(new Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                try{
                    if (response.body().getStatus_code().equals("1")) {
                        setSuccessMessage("A verification has been sent to your email");
                    }else if(response.body().getStatus_code().equals("4")){
                        setErrorMessage("Email address already registered");
                    }else{
                        setErrorMessage("Error "+ response.body().getStatus_code() +" : "+response.body().getStatus());
                    }
                    hideProgressBar();
                }catch (Exception e){
                    hideProgressBar();
                }
            }
            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    hideProgressBar();
                    Toast.makeText(RegisterActivity.this, "Error, please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
