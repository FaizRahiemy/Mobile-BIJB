package com.volunteam.mobilebijb.AirportBike;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.volunteam.mobilebijb.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AirportBikeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_bike)
    Toolbar toolbarBike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_bike);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarBike);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarBike.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        checkCameraPermission();
    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA )
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)){
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA
                }, 98);
            }else{
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA
                }, 98);
            }
            return false;
        }else{
            return true;
        }
    }
}
