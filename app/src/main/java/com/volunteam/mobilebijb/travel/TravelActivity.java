package com.volunteam.mobilebijb.travel;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class TravelActivity extends AppCompatActivity implements TravelActivityPresenter.View{

    private Toolbar toolbar;
    private RecyclerView recyclerTravel;
    private TravelAdapter travelAdapter;
    private TravelActivityPresenter presenter;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        presenter = new TravelActivityPresenter(this);

        // init views
        defineViews();

        presenter.getTravelData();
    }

    private void defineViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Traveling");

        recyclerTravel = (RecyclerView)findViewById(R.id.recycler_travel);
        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipe_travel);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getTravelData();
            }
        });
        swipeContainer.setColorSchemeColors(Color.parseColor("#005c97"),
                Color.parseColor("#363759"));

        travelAdapter = new TravelAdapter(presenter.getResultTravels());
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(TravelActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerTravel.setLayoutManager(layoutManager);
        recyclerTravel.setItemAnimator(new DefaultItemAnimator());
        recyclerTravel.setAdapter(travelAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void showTravelData() {
        travelAdapter.notifyDataSetChanged();
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
        Toast.makeText(TravelActivity.this,message,Toast.LENGTH_SHORT).show();
    }
}
