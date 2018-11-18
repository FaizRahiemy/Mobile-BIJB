package com.volunteam.mobilebijb.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.Transaksi.DaftarTransaksiActivity;
import com.volunteam.mobilebijb.config.TinyDB;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, ProfileActivityPresenter.View{

    private ProfileActivityPresenter presenter;
    private EditText edtNama, edtEmail, edtPass, edtCPass, edtPin, edtCPin;
    private TextInputLayout txInputNama, txInputEmail, txInputPass, txInputCPass,
                            txInputPin, txInputCPin;
    private Button btnUpdate, btnTopup, btnTransaksi;
    private TextView txSaldo;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private TinyDB tinyDB;
    private String token = "";
    private String id = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // get id and token from sharedpreference
        tinyDB = new TinyDB(this);
        try{
            token = tinyDB.getString("token");
            id = tinyDB.getString("id");
        }catch (Exception e){
            Toast.makeText(ProfileActivity.this,"There is an error, please re-login",
                    Toast.LENGTH_SHORT).show();
        }
        presenter = new ProfileActivityPresenter(this,id,token);

        defineViews();

        presenter.getUser();
    }

    private void defineViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edtNama = (EditText)findViewById(R.id.edt_nama_profile);
        edtEmail = (EditText)findViewById(R.id.edt_email_profile);
        edtPass = (EditText)findViewById(R.id.edt_password_profile);
        edtCPass = (EditText)findViewById(R.id.edt_cpassword_profile);
        edtPin = (EditText)findViewById(R.id.edt_pin_profile);
        edtCPin = (EditText)findViewById(R.id.edt_cpin_profile);
        txInputNama = (TextInputLayout)findViewById(R.id.txinput_nama_profile);
        txInputEmail = (TextInputLayout)findViewById(R.id.txinput_email_profile);
        txInputPass = (TextInputLayout)findViewById(R.id.txinput_password_profile);
        txInputCPass = (TextInputLayout)findViewById(R.id.txinput_cpassword_profile);
        txInputPin = (TextInputLayout)findViewById(R.id.txinput_pin_profile);
        txInputCPin = (TextInputLayout)findViewById(R.id.txinput_cpin_profile);
        txSaldo = (TextView)findViewById(R.id.tx_saldo_profile);
        btnUpdate = (Button)findViewById(R.id.btn_update_profile);
        btnTransaksi = (Button)findViewById(R.id.btn_transaksi);
        progressBar = (ProgressBar)findViewById(R.id.pb_loading);
        btnUpdate.setOnClickListener(this);
        btnTransaksi.setOnClickListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_update_profile:
                // TODO validation field
                if (!isErrorInput()){
                    presenter.updateUser(new User(edtNama.getText().toString(),
                            edtEmail.getText().toString(),
                            edtPass.getText().toString(),
                            edtPin.getText().toString()));
                }
                break;
            case R.id.btn_transaksi:
                intent = new Intent(getApplicationContext(), DaftarTransaksiActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void showUserData(User user) {
        setTitle(user.getNama());
        edtNama.setText(user.getNama());
        edtEmail.setText(user.getEmail());
        edtPass.setText("");
        edtCPass.setText("");
        edtPin.setText("");
        edtCPin.setText("");
        txSaldo.setText(user.getSaldo());
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private boolean isErrorInput(){
        // check error on input field
        boolean isError = false;
        if (isEmpty(edtNama)){
            setErrorMessage(txInputNama, "Name cannot be empty");
            isError = true;
        }else{
            txInputNama.setErrorEnabled(false);
        }

        if (isEmpty(edtEmail)){
            setErrorMessage(txInputEmail, "Email cannot be empty");
            isError = true;
        }else{
            txInputEmail.setErrorEnabled(false);
        }

        if (isEmpty(edtCPass)&&!isEmpty(edtPass)){
            setErrorMessage(txInputCPass, "Confirm Password cannot be empty");
            isError = true;
        }else if(!isEmpty(edtPass)&&!isEmpty(edtCPass)){
            if (edtPass.getText().toString().equals(edtCPass.getText().toString())) {
                txInputCPass.setErrorEnabled(false);
            } else {
                setErrorMessage(txInputCPass, "Confirm Password is incorrect");
                isError = true;
            }
        }

        if (isEmpty(edtCPin)&&!isEmpty(edtPin)){
            setErrorMessage(txInputCPin, "Confirm Pin cannot be empty");
            isError = true;
        }else if (!isEmpty(edtCPin)&&!isEmpty(edtPin)){
            if (edtPin.getText().toString().equals(edtCPin.getText().toString())) {
                txInputCPin.setErrorEnabled(false);
            } else {
                setErrorMessage(txInputCPin, "Confirm Pin is incorrect");
                isError = true;
            }
        }
        return isError;
    }

    private void setErrorMessage(TextInputLayout txInput, String message){
        txInput.setErrorEnabled(true);
        txInput.setError(message);
    }

    private void dummyLoading(){
        // dummy loading
        final int interval = 1000; // 1 Second
        Handler handler = new Handler();
        Runnable runnable = new Runnable(){
            public void run() {
                hideProgressBar();
            }
        };
        handler.postAtTime(runnable, System.currentTimeMillis()+interval);
        handler.postDelayed(runnable, interval);
    }
}
