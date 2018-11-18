package com.volunteam.mobilebijb.travelDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.volunteam.mobilebijb.R;

public class TravelDetailActivity extends AppCompatActivity implements DetailTravelPresenter.View, View.OnClickListener {

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private DetailTravelPresenter presenter;
    private String idTravel = "0";

    private ImageView imgHeader, imgLike, imgUnlike;
    private TextView txDesc;
    private RecyclerView recyclerPost;
    private FloatingActionButton fab;
    private LinearLayout layoutKomentar, layoutUlasan;
    private RelativeLayout layoutFav;
    private TextView btnSendKomentar;
    private RatingBar ratePost;
    private EditText edtKomentar;
    private KomentarAdapter adapterKomentar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_detail);
        presenter = new DetailTravelPresenter(this);

        try{
            idTravel = getIntent().getStringExtra("idTravel");
        }catch (Exception e){
            showMessage("There is an error, please re-login");
        }

        defineViews();

        presenter.getTraverDetail(idTravel);
    }

    private void defineViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_travel_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        layoutKomentar = (LinearLayout) findViewById(R.id.layout_komentar);
        txDesc = (TextView) findViewById(R.id.tx_desc_post);
        imgHeader = (ImageView) findViewById(R.id.header_post);
        ratePost = (RatingBar) findViewById(R.id.rate_post);
        btnSendKomentar  = (TextView) findViewById(R.id.btnSendKomentar);
        edtKomentar = (EditText) findViewById(R.id.etKomentar);
        recyclerPost = (RecyclerView) findViewById(R.id.recyclerKomentar);
        layoutUlasan = (LinearLayout) findViewById(R.id.layout_ulasan);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_post);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar_post);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    //collapsingToolbarLayout.setTitle(" ");
                    isShow = true;
                } else if(isShow) {
                    //collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        adapterKomentar = new KomentarAdapter(presenter.getKomentarList());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TravelDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerPost.setLayoutManager(layoutManager);
        recyclerPost.setItemAnimator(new DefaultItemAnimator());
        recyclerPost.setAdapter(adapterKomentar);

        progressBar = findViewById(R.id.pb_loading);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void showTravelDetail(ResultTravelDetail travelDetail) {
        //TODO settile according to travel name
        getSupportActionBar().setTitle(travelDetail.getNama());
        if (travelDetail.getImage().isEmpty()) {
            imgHeader.setImageResource(R.color.colorPrimaryDark);
        } else {
            Picasso.with(getApplicationContext())
                    .load(travelDetail.getImage())
                    .placeholder(R.color.colorPrimaryDark)
                    .error(android.R.color.transparent)
                    .into(imgHeader);
        }
        adapterKomentar.notifyDataSetChanged();
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
        Toast.makeText(TravelDetailActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                Intent intent = new Intent(TravelDetailActivity.this,RuteWisataActivity.class);
                intent.putExtra("lat",presenter.getTravelDetails().getLat());
                intent.putExtra("lon",presenter.getTravelDetails().getLon());
                startActivity(intent);
                break;
        }
    }
}
