package com.volunteam.mobilebijb.myflight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.config.TinyDB;

public class AddFlightActivity extends AppCompatActivity implements AddFlightActivityPresenter.View, View.OnClickListener{

    private Toolbar toolbar;
    private AddFlightActivityPresenter presenter;
    private EditText edtKode, edtKTP;
    private ProgressBar progressBar;
    private TextInputLayout txInputKode, txInputKTP;
    private Button btnAddFlight;
    private String id = "";
    private String token = "";
    private TinyDB tinyDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);

        // get id and token from sharedpreference
        tinyDB = new TinyDB(this);
        try{
            token = tinyDB.getString("token");
            id = tinyDB.getString("id");
        }catch (Exception e){
            Toast.makeText(AddFlightActivity.this,"There is an error, please re-login",
                    Toast.LENGTH_SHORT).show();
        }

        // init views
        defineViews();
        presenter = new AddFlightActivityPresenter(this);
    }

    public void defineViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Add Flight");

        edtKode = findViewById(R.id.edt_kode_flight);
        edtKTP = findViewById(R.id.edt_ktp_flight);
        txInputKode = findViewById(R.id.txinput_kode_flight);
        txInputKTP = findViewById(R.id.txinput_ktp_flight);
        btnAddFlight = findViewById(R.id.btn_add_flight);
        progressBar = findViewById(R.id.pb_loading);
        btnAddFlight.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_flight:
                if (!isErrorInput()){
                    presenter.addFlightData(id, token,
                            edtKode.getText().toString(),
                            edtKTP.getText().toString()
                    );
                }
                break;
        }
    }

    @Override
    public void clearField() {
        edtKode.setText("");
        edtKTP.setText("");
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
        Toast.makeText(AddFlightActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isErrorInput() {
        // check error on input field
        boolean isError = false;
        if (isEmpty(edtKode)) {
            setErrorMessage(txInputKode, "Kode penerbangan cannot be empty");
            isError = true;
        } else {
            txInputKode.setErrorEnabled(false);
        }

        if (isEmpty(edtKTP)) {
            setErrorMessage(txInputKTP, "KTP cannot be empty");
            isError = true;
        } else {
            txInputKTP.setErrorEnabled(false);
        }
        return isError;
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void setErrorMessage(TextInputLayout txInput, String message){
        txInput.setErrorEnabled(true);
        txInput.setError(message);
    }
}
