package com.volunteam.mobilebijb.Travelling.RuteActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MapsAPIHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RuteActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    @BindView(R.id.toolbar_rute)
    Toolbar toolbarRute;
    @BindView(R.id.tx_waktu)
    TextView txWaktu;
    @BindView(R.id.tx_jarak)
    TextView txJarak;
    @BindView(R.id.fab_rute)
    FloatingActionButton fabRute;

    API api;
    ProgressDialog mdialog;

    //Buat Maps
    private GoogleMap mMap;
    ArrayList<LatLng> markerPoints;
    LatLng destionationloc,originloc;

    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    boolean udah = false;

    private List<Polyline> polylines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rute);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarRute);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarRute.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mdialog = ProgressDialog.show(RuteActivity.this, "",
                "Loading. Please wait...", true);

        mdialog.show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkLocationPermission();
        }

        if (Build.VERSION.SDK_INT > 8){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        api = MapsAPIHelper.getClient().create(API.class);
        originloc = new LatLng(-6.920335,107.658218);
//        if (getIntent().hasExtra("lat")){
//            destionationloc = new LatLng(Float.parseFloat(getIntent().getStringExtra("lat")),Float.parseFloat(getIntent().getStringExtra("lon")));
//        }
        destionationloc = new LatLng(-6.6526744,108.1577072);

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

    @OnClick(R.id.fab_rute)
    public void onViewClicked() {
        String geoUri = "https://www.google.com/maps/dir/?api=1&origin=" + originloc.latitude + "," + originloc.longitude + "&destination=" + destionationloc.latitude + "," + destionationloc.longitude + "&travelmode=driving";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(geoUri));
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originloc, 13));
            }
        }else{
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originloc, 13));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        originloc = new LatLng(location.getLatitude(), location.getLongitude());
        if (!udah){
            routing();

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
                == PackageManager.PERMISSION_GRANTED){
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

    protected synchronized void buildGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION )
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 99);
            }else{
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 99);
            }
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 99 : {
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
                }else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void routing(){

        final String str = "https://maps.googleapis.com/maps/api/directions/json?"
                + "origin=" + originloc.latitude + "," + originloc.longitude
                + "&destination=" + destionationloc.latitude + "," + destionationloc.longitude
                +"&language=" +getResources().getConfiguration().locale.getDefault().getLanguage()
                + "&sensor=false&units=metric&mode=" + "driving"
                + "&alternatives=true&key=" + getResources().getString(R.string.google_maps_key);

        try {
            URL url = new URL(str);
            HttpURLConnection conn =(HttpURLConnection) url.openConnection();
            InputStreamReader in = null;
            in = new InputStreamReader(conn.getInputStream());

            StringBuilder jsonResults = new StringBuilder();
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }

            MarkerOptions destMarker = new MarkerOptions();
            destMarker.position(destionationloc);
            destMarker.icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.ic_pin_airport));
            destMarker.title("BIJB");
            mMap.addMarker(destMarker);

            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray parentArray = jsonObj.getJSONArray("routes");
            final JSONArray legArray = parentArray.getJSONObject(0).getJSONArray("legs");

            //Distance
            JSONObject distanceObj = legArray.getJSONObject(0).getJSONObject("distance");
            txJarak.setText(distanceObj.getString("text"));

            //Distance
            JSONObject durationObj = legArray.getJSONObject(0).getJSONObject("duration");
            txWaktu.setText(durationObj.getString("text"));

            // Fetching 1st route
            JSONArray steps = legArray.getJSONObject(0).getJSONArray("steps");
            ArrayList<LatLng> route = new ArrayList<>();
            PolylineOptions lineOptions = new PolylineOptions();
            int color = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);
            // Fetching all the points in 1st route
            for (int j = 0; j < steps.length(); j++) {
                String polyline;
                polyline = (String) ((JSONObject) ((JSONObject) steps
                        .get(j)).get("polyline")).get("points");
                List<LatLng> list = decodePoly(polyline);

                /** Traversing all points */
                for (int l = 0; l < list.size(); l++) {
                    HashMap<String, String> position = new HashMap<>();
                    position.put("lat",
                            Double.toString(( list.get(l)).latitude));
                    position.put("lng",
                            Double.toString(( list.get(l)).longitude));
                    route.add(new LatLng(list.get(l).latitude, list.get(l).longitude));
                }
                //builder.include(position);
            }

            Polyline polyline = mMap.addPolyline(new PolylineOptions()
                    .color(color)
                    .clickable(true)
                    .addAll(route));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }
}
