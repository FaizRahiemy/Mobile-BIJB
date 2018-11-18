package com.volunteam.mobilebijb.myflight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.volunteam.mobilebijb.R;

public class DetailMyFlightActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;

    private TextView txtKodeBooking;

    private TextView txtAirline;
    private ImageView imgAirline;

    private TextView txtWaktuDeparture, tctTanggalDeparture, txtTempatDeparture;
    private TextView txtWaktuArrival, txtTanggalArrival, txtTempatArrival;

    private RecyclerView recyclerPenumpang;

    private LinearLayout btnBoardingPass;
    private TextView txtBoardingPass, txtLihatBoardingPass;

    private LinearLayout btnLayananBagasi;
    private TextView txtLayananBagasi, txtGunakanBagasi;

    private LinearLayout layoutBtnCheckin;
    private Button btnCheckin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_my_flight);

        defineViews();
        //setIntentToView();
    }

    private void defineViews(){
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);

        txtKodeBooking = findViewById(R.id.txtKodeBooking);

        txtAirline = findViewById(R.id.txtAirline);
        imgAirline = findViewById(R.id.imgAirline);

        txtWaktuDeparture = findViewById(R.id.txtWaktuDeparture);
        tctTanggalDeparture = findViewById(R.id.tctTanggalDeparture);
        txtTempatDeparture = findViewById(R.id.txtTempatDeparture);
        txtWaktuArrival = findViewById(R.id.txtWaktuArrival);
        txtTanggalArrival = findViewById(R.id.txtTanggalArrival);
        txtTempatArrival = findViewById(R.id.txtTempatArrival);

        recyclerPenumpang = findViewById(R.id.recyclerPenumpang);

        btnBoardingPass = findViewById(R.id.btnBoardingPass);
        txtBoardingPass = findViewById(R.id.txtBoardingPass);
        txtLihatBoardingPass = findViewById(R.id.txtLihatBoardingPass);

        btnLayananBagasi = findViewById(R.id.btnLayananBagasi);
        txtLayananBagasi = findViewById(R.id.txtLayananBagasi);
        txtGunakanBagasi = findViewById(R.id.txtGunakanBagasi);

        layoutBtnCheckin = findViewById(R.id.layoutBtnCheckin);
        btnCheckin = findViewById(R.id.btnCheckin);
        btnCheckin.setOnClickListener(this);
    }

    private void setToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Flight Details");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private String getIntentKodeBooking(){
        return getIntent().getStringExtra("KODE_BOOKING");
    }

    private String getIntentAirline(){
        return getIntent().getStringExtra("AIRLINE");
    }

    private String getIntentImgAirline(){
        return getIntent().getStringExtra("IMG_AIRLINE");
    }

    private String getIntentTimeDeparture(){
        return getIntent().getStringExtra("TIME_DEPARTURE");
    }

    private String getIntentTimeArrival(){
        return getIntent().getStringExtra("TIME_ARRIVAL");
    }

    private String tglDeparture(){
        return getIntent().getStringExtra("TGL_DEPARTURE");
    }

    private String tglArrival(){
        return getIntent().getStringExtra("TGL_ARRIVAL");
    }

    private String getFrom(){
        return getIntent().getStringExtra("FROM");
    }

    private String getTo(){
        return getIntent().getStringExtra("TO");
    }

    private void setIntentToView(){
        txtKodeBooking.setText(getIntentKodeBooking());
        txtAirline.setText(getIntentAirline());
        txtWaktuDeparture.setText(getIntentTimeDeparture());
        txtWaktuArrival.setText(getIntentTimeArrival());
        tctTanggalDeparture.setText(tglDeparture());
        txtTanggalArrival.setText(tglArrival());
        txtTempatDeparture.setText(getFrom());
        txtTempatArrival.setText(getTo());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCheckin:
                startActivity(new Intent(DetailMyFlightActivity.this,PilihKursiActivity.class));
        }
    }
}
