package com.volunteam.mobilebijb.home;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.smarteist.autoimageslider.SliderLayout;
import com.volunteam.mobilebijb.AirportBike.AirportBikeActivity;
import com.volunteam.mobilebijb.BookTicket.BookTicketActivity;
import com.volunteam.mobilebijb.BookTicket.PickDateActivity;
import com.volunteam.mobilebijb.LayananBagasi.LayananBagasiActivity;
import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.Travelling.TravellingActivity.TravellingActivity;
import com.volunteam.mobilebijb.chat.ChatActivity;
import com.volunteam.mobilebijb.config.TinyDB;
import com.volunteam.mobilebijb.config.TinyDB;
import com.volunteam.mobilebijb.config.TinyDB;
import com.volunteam.mobilebijb.config.notification.Config;
import com.volunteam.mobilebijb.config.notification.NotificationUtils;
import com.volunteam.mobilebijb.entertainment.EntertainmentActivity;
import com.volunteam.mobilebijb.entertainment.adapters.BeritaAdapter;
import com.volunteam.mobilebijb.entertainment.adapters.MovieAdapter;
import com.volunteam.mobilebijb.entertainment.models.news.Article;
import com.volunteam.mobilebijb.entertainment.models.movie.Result;
import com.volunteam.mobilebijb.login.LoginActivity;
import com.volunteam.mobilebijb.login.LoginActivity;
import com.volunteam.mobilebijb.login.LoginActivity;
import com.volunteam.mobilebijb.merchandise.MerchandiseActivity;
import com.volunteam.mobilebijb.home.adapter.MerchandiseHomeAdapter;
import com.volunteam.mobilebijb.home.adapter.MyFlightInfoAdapter;
import com.volunteam.mobilebijb.home.model.DummyFlightInfo;
import com.volunteam.mobilebijb.merchandise.pojo.MerchsItem;

import com.volunteam.mobilebijb.myflight.MyFlightActivity;
import com.volunteam.mobilebijb.parking.ParkingActivity;
import com.volunteam.mobilebijb.profile.ProfileActivity;
import com.volunteam.mobilebijb.travel.TravelActivity;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HomeView, View.OnClickListener {

    //collapsing toolbar
    private Toolbar toolbar;
    private TextView txt_suhu, txt_cuaca;
    private ImageView img_cuaca;

    //flight info
    private RecyclerView recycler_flight_info;
    private RelativeLayout btn_lihat_semua;

    //slider layout
    private SliderLayout slider_layout;

    //marchendise
    private TextView btn_lihat_semua_marchendise;
    private RecyclerView recycler_marchendise;

    //film
    private TextView btn_lihat_semua_film;
    private RecyclerView recycler_film;

    //berita
    private TextView btn_lihat_semua_berita;
    private RecyclerView recycler_berita;

    //sidebar
    private NavigationView nav_view;
    private DrawerLayout home_layout;
    private TextView txProfile;

    //presenter
    private HomePresenter homePresenter = new HomePresenter(this);

    //push notif
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private final String apiKeyNews = "261d82dd7e26494e841fb1039a4fdaf7";
    private final String apiKeyMovie = "4b9bfb0e83de2a4afb17c157ccb254f3";

    private ProgressDialog mProgressDialog;

//    account
    private TinyDB tinyDB;
    private String token = "";
    private String id = "";

    private Menu myMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        checkLocationPermission();
        checkCameraPermission();
        tinyDB = new TinyDB(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkLocationPermission();
            checkCameraPermission();
        }

        defineViews();
        setToolbar(toolbar);
        homePresenter.getFlightInfo();
        homePresenter.getMerchandise();
        homePresenter.getNowPlayingMovies(apiKeyMovie);
        homePresenter.getBerita("id",apiKeyNews);
        setBroadcast();
        displayFirebaseRegId();
        token = tinyDB.getString("token");
        id = tinyDB.getString("id");
        if (id!="" && token!=""){
            homePresenter.getUser(token, id);

        }
    }

    private void defineViews(){
        //collapsing toolbar
        toolbar = findViewById(R.id.toolbar);
        txt_suhu = findViewById(R.id.txt_suhu);
        txt_cuaca = findViewById(R.id.txt_cuaca);
        img_cuaca = findViewById(R.id.img_cuaca);

        //flight info
        recycler_flight_info = findViewById(R.id.recycler_flight_info);
        btn_lihat_semua = findViewById(R.id.btn_lihat_semua);
        btn_lihat_semua.setOnClickListener(this);

        //slider layout
        slider_layout = findViewById(R.id.slider_layout);

        //marchendise
        btn_lihat_semua_marchendise = findViewById(R.id.btn_lihat_semua_marchendise);
        recycler_marchendise = findViewById(R.id.recycler_marchendise);
        btn_lihat_semua_marchendise.setOnClickListener(this);

        //film
        btn_lihat_semua_film = findViewById(R.id.btn_lihat_semua_film);
        recycler_film = findViewById(R.id.recycler_film);
        btn_lihat_semua_film.setOnClickListener(this);

        //berita
        btn_lihat_semua_berita = findViewById(R.id.btn_lihat_semua_berita);
        recycler_berita = findViewById(R.id.recycler_berita);
        btn_lihat_semua_berita.setOnClickListener(this);

        //sidebar
        nav_view = findViewById(R.id.nav_view);
        home_layout = findViewById(R.id.home_layout);
        nav_view.setNavigationItemSelectedListener(this);
        myMenu = nav_view.getMenu();
        txProfile = (TextView)findViewById(R.id.tx_profile);
        View headerView = nav_view.getHeaderView(0);
        ImageView imgProfile = (ImageView) headerView.findViewById(R.id.img_profile);
        txProfile = (TextView) headerView.findViewById(R.id.tx_profile);
        // TODO set name according to login user
        txProfile.setText("Login");
        imgProfile.setOnClickListener(this);
        txProfile.setOnClickListener(this);

        try{
            if (tinyDB.getString("id").equals("")) {
                myMenu.findItem(R.id.logout).setVisible(false);
            }else{
                myMenu.findItem(R.id.logout).setVisible(true);
            }
        }catch(Exception e){
            myMenu.findItem(R.id.logout).setVisible(false);
        }
    }

    private void setToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
    }

    private void setBroadcast(){
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");
                    System.out.println("message notification: "+message);
                }
            }
        };
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        System.out.println("Firebase reg id: " + regId);
    }

    public TextView getTxProfile() {
        return txProfile;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                home_layout.openDrawer(Gravity.LEFT);
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        home_layout.closeDrawer(Gravity.LEFT);
        switch (menuItem.getItemId()){
            case R.id.flight_info:
                Toast.makeText(this, "menu flight info", Toast.LENGTH_SHORT).show();
                break;
            case R.id.baggage_track:
                intent = new Intent(getApplicationContext(), LayananBagasiActivity.class);
                getApplicationContext().startActivity(intent);
                break;
            case R.id.my_flight:
                startActivity(new Intent(HomeActivity.this, MyFlightActivity.class));
                break;
            case R.id.book_ticket:
                intent = new Intent(getApplicationContext(), PickDateActivity.class);
                getApplicationContext().startActivity(intent);
                break;
            case R.id.buy_souvenier:
                startActivity(new Intent(HomeActivity.this, MerchandiseActivity.class));
                break;
            case R.id.book_parking:
                startActivity(new Intent(HomeActivity.this, ParkingActivity.class));
                break;
            case R.id.airport_bike:
                intent = new Intent(getApplicationContext(), AirportBikeActivity.class);
                getApplicationContext().startActivity(intent);
                break;
            case R.id.entertainment_news:
                startActivity(new Intent(HomeActivity.this, EntertainmentActivity.class));
                break;
            case R.id.traveling:
                startActivity(new Intent(HomeActivity.this, TravellingActivity.class));
                break;
            case R.id.customer_service:
                startActivity(new Intent(HomeActivity.this, ChatActivity.class));
                break;
            case R.id.about:
                Toast.makeText(this, "menu about", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                tinyDB.remove("id");
                tinyDB.remove("token");
                startActivity(new Intent(HomeActivity.this,HomeActivity.class));
                finish();
                break;
        }
        return true;
    }

    @Override
    public void setFlightInfo(List<DummyFlightInfo> dummyFlightInfoList) {
        MyFlightInfoAdapter myFlightInfoAdapter = new MyFlightInfoAdapter(dummyFlightInfoList);
        RecyclerView.LayoutManager layoutManagerOption = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_flight_info.setLayoutManager(layoutManagerOption);
        recycler_flight_info.setItemAnimator(new DefaultItemAnimator());
        recycler_flight_info.setAdapter(myFlightInfoAdapter);
        System.out.println("jumlah my flight: "+String.valueOf(myFlightInfoAdapter.getItemCount()));
    }

    @Override
    public void setMerchandise(List<MerchsItem> merchsList) {
        MerchandiseHomeAdapter merchandiseHomeAdapter = new MerchandiseHomeAdapter(merchsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler_marchendise.setLayoutManager(layoutManager);
        recycler_marchendise.setItemAnimator(new DefaultItemAnimator());
        recycler_marchendise.setAdapter(merchandiseHomeAdapter);
        System.out.println("jumlah marchendise: "+String.valueOf(merchandiseHomeAdapter.getItemCount()));
    }

    @Override
    public void setBerita(List<Article> articleList) {
        BeritaAdapter beritaAdapter = new BeritaAdapter(articleList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_berita.setLayoutManager(layoutManager);
        recycler_berita.setItemAnimator(new DefaultItemAnimator());
        recycler_berita.setAdapter(beritaAdapter);
        System.out.println("jumlah berita: "+String.valueOf(beritaAdapter.getItemCount()));
        dismissProgressDialog();
    }

    @Override
    public void setFilmSortByPopularity(List<Result> filmSortByPopularity) {
        MovieAdapter movieNewAdapter = new MovieAdapter(filmSortByPopularity);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler_film.setLayoutManager(layoutManager);
        recycler_film.setItemAnimator(new DefaultItemAnimator());
        recycler_film.setAdapter(movieNewAdapter);
        System.out.println("jumlah film: "+String.valueOf(movieNewAdapter.getItemCount()));
        dismissProgressDialog();
    }

    @Override
    public void getUser(String token, String id) {

    }

    @Override
    public void onFailureGetFilm(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailureGetBerita(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailureGetMerchandise(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_lihat_semua:
                Toast.makeText(this, "lihat semua flight info", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_lihat_semua_berita:
                /*Toast.makeText(this, "lihat semua berita", Toast.LENGTH_SHORT).show();*/
                startActivity(new Intent(this, EntertainmentActivity.class));
                break;
            case R.id.btn_lihat_semua_film:
                /*Toast.makeText(this, "lihat semua film", Toast.LENGTH_SHORT).show();*/
                startActivity(new Intent(this, EntertainmentActivity.class));
                break;
            case R.id.btn_lihat_semua_marchendise:
                /*Toast.makeText(this, "lihat semua marchendise", Toast.LENGTH_SHORT).show();*/
                startActivity(new Intent(HomeActivity.this, MerchandiseActivity.class));
                break;
            case R.id.img_profile:
                try {
                    token = tinyDB.getString("token");
                    id = tinyDB.getString("id");
                    if (token.equals("")&&id.equals("")){
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else{
                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                    }
                }catch (Exception e){
                    Toast.makeText(this, "An error occured!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tx_profile:
                tinyDB = new TinyDB(this);
                try {
                    token = tinyDB.getString("token");
                    id = tinyDB.getString("id");
                    if (token.equals("")&&id.equals("")){
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else{
                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                    }
                }catch (Exception e){
                    Toast.makeText(this, "An error occured!", Toast.LENGTH_SHORT).show();
                }
                break;
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
}
