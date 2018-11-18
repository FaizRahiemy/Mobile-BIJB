package com.volunteam.mobilebijb.LayananBagasi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.volunteam.mobilebijb.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaggageTrackerActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_bagasi)
    Toolbar toolbarBagasi;
    @BindView(R.id.button_cari_bagasi)
    Button buttonCariBagasi;
    @BindView(R.id.et_kode_bagasi)
    EditText etKodeBagasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baggage_tracker);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarBagasi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarBagasi.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @OnClick(R.id.button_cari_bagasi)
    public void onViewClicked() {
        if (etKodeBagasi.getText().length() == 0){
            Toast.makeText(this, "Kode Bagasi tidak boleh kosong!", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(getApplicationContext(), LayananBagasiDetailActivity.class);
            intent.putExtra("kode", String.valueOf(etKodeBagasi.getText()));
            getApplicationContext().startActivity(intent);
        }
    }
}
