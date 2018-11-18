package com.volunteam.mobilebijb.myflight;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.config.TinyDB;

public class MyFlightActivity extends AppCompatActivity implements View.OnClickListener,MyFlightActivityPresenter.View {

    private Toolbar toolbar;
    private RecyclerView recyclerFlight;
    private MyFlightAdapter myFlightAdapter;
    private MyFlightActivityPresenter presenter;
    private SwipeRefreshLayout swipeContainer;
    private FloatingActionButton fabAddFlight;
    private LinearLayout layoutEmpty;
    private String id = "";
    private String token = "";
    private TinyDB tinyDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myflight);

        // get id and token from sharedpreference
        tinyDB = new TinyDB(this);
        try{
            token = tinyDB.getString("token");
            id = tinyDB.getString("id");
        }catch (Exception e){
            Toast.makeText(MyFlightActivity.this,"There is an error, please re-login",
                    Toast.LENGTH_SHORT).show();
        }
        presenter = new MyFlightActivityPresenter(this);

        // init views
        defineViews();

        presenter.getMyFlightData(id,token);

        if (presenter.getMyFlightList().size() < 1){
            layoutEmpty.setVisibility(View.VISIBLE);
        }else {
            layoutEmpty.setVisibility(View.INVISIBLE);
        }
    }

    private void defineViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("My Flight");

        layoutEmpty = findViewById(R.id.layout_empty_myflight);
        fabAddFlight = findViewById(R.id.fab_myflight);
        fabAddFlight.setOnClickListener(this);
        recyclerFlight = (RecyclerView)findViewById(R.id.recycler_myflight);
        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipe_myflight);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getMyFlightData(id,token);
            }
        });
        swipeContainer.setColorSchemeColors(Color.parseColor("#005c97"),
                Color.parseColor("#363759"));

        myFlightAdapter = new MyFlightAdapter(presenter.getMyFlightList());
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(MyFlightActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerFlight.setLayoutManager(layoutManager);
        recyclerFlight.setItemAnimator(new DefaultItemAnimator());
        recyclerFlight.setAdapter(myFlightAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void showFlightsData() {
        myFlightAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRefresh() {
        swipeContainer.setRefreshing(true);
    }

    @Override
    public void hideRefresh() {
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(MyFlightActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_myflight:
                Intent intent = new Intent(MyFlightActivity.this, AddFlightActivity.class);
                startActivity(intent);
                break;
        }
    }
}
