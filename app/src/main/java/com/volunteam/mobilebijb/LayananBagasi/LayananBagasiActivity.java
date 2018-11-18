package com.volunteam.mobilebijb.LayananBagasi;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.Travelling.RuteActivity.MapFragment;
import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MapsAPIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LayananBagasiActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    @BindView(R.id.toolbar_bagasi)
    Toolbar toolbarBagasi;
    @BindView(R.id.et_kode_booking_penerbangan)
    EditText etKodeBookingPenerbangan;
    @BindView(R.id.et_kode_alamat_detail)
    EditText etKodeAlamatDetail;
    @BindView(R.id.button_pesan_layanan_bagasi)
    Button buttonPesanLayananBagasi;
    @BindView(R.id.check_bagasi_bandara)
    CheckBox checkBagasiBandara;

    API api;
    ProgressDialog mdialog;
    int checkState = 0;
    Drawable originalEtStyle;

    //Buat Maps
    private GoogleMap mMap;
    LatLng originloc;

    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    boolean udah = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan_bagasi);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarBagasi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarBagasi.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        originalEtStyle = etKodeAlamatDetail.getBackground();

        mdialog = ProgressDialog.show(LayananBagasiActivity.this, "",
                "Loading. Please wait...", true);

        mdialog.show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        if (Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        api = MapsAPIHelper.getClient().create(API.class);
        originloc = new LatLng(-6.920335, 107.658218);

        if (mMap == null) {
            MapFragment mapFragment = ((MapFragment) getSupportFragmentManager().findFragmentById(R.id.map));

            ((MapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .setListener(new MapFragment.OnTouchListener() {
                        @Override
                        public void onTouch() {

                        }
                    });
            mapFragment.getMapAsync(this);
        }

        mdialog.dismiss();
    }

    @OnClick({R.id.check_bagasi_bandara, R.id.button_pesan_layanan_bagasi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.check_bagasi_bandara:
                if (checkState == 0){
                    checkState = 1;
                    etKodeAlamatDetail.setFocusable(false);
                    etKodeAlamatDetail.setFocusableInTouchMode(false);
                    etKodeAlamatDetail.setBackgroundColor(Color.parseColor("#cdcdcd"));
                }else{
                    checkState = 0;
                    etKodeAlamatDetail.setFocusable(true);
                    etKodeAlamatDetail.setFocusableInTouchMode(true);
                    etKodeAlamatDetail.setBackground(originalEtStyle);
                }
                break;
            case R.id.button_pesan_layanan_bagasi:
                if (etKodeBookingPenerbangan.getText().length() == 0) {
                    Toast.makeText(this, "Kode Bagasi tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    if (checkState == 0){
                        if (etKodeAlamatDetail.getText().length() == 0){
                            Toast.makeText(this, "Alamat detail harus diisi!", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent(getApplicationContext(), LayananBagasiDetailActivity.class);
                            intent.putExtra("kode", String.valueOf(etKodeBookingPenerbangan.getText()));
                            getApplicationContext().startActivity(intent);
                        }
                    }else{
                        Intent intent = new Intent(getApplicationContext(), LayananBagasiDetailActivity.class);
                        intent.putExtra("kode", String.valueOf(etKodeBookingPenerbangan.getText()));
                        getApplicationContext().startActivity(intent);
                    }
                }
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originloc, 13));
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originloc, 13));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        originloc = new LatLng(location.getLatitude(), location.getLongitude());
        if (!udah) {

            udah = true;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates
                    (googleApiClient, locationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 99);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 99);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 99: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                        if (googleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
