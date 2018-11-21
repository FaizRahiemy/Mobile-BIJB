package com.volunteam.mobilebijb.myflight;

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
import android.widget.Button;
import android.widget.Toast;

import com.volunteam.mobilebijb.R;

public class PilihKursiActivity extends AppCompatActivity implements PilihKursiActivityPresenter.View, View.OnClickListener {

    private Toolbar toolbar;
    private RecyclerView recyclerKursi;
    private KursiAdapter kursiAdapter;
    private SwipeRefreshLayout swipeContainer;
    private PilihKursiActivityPresenter presenter;
    private Button btnSelesai;
    private String idFlidght = "";
    private int num = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_kursi);
        presenter = new PilihKursiActivityPresenter(this);

        if (getIntent().hasExtra("num")){
            num = Integer.parseInt(getIntent().getStringExtra("num"));
        }

        // init views
        defineViews();

        presenter.getKursiData(idFlidght);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void defineViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Pilih Kursi");

        btnSelesai = findViewById(R.id.btn_add_kursi);
        btnSelesai.setOnClickListener(this);

        recyclerKursi = (RecyclerView)findViewById(R.id.recycler_kursi);
        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipe_kursi);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getKursiData(idFlidght);
            }
        });
        swipeContainer.setColorSchemeColors(Color.parseColor("#005c97"),
                Color.parseColor("#363759"));

        kursiAdapter = new KursiAdapter(presenter.getRowKursiList(), num);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(PilihKursiActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerKursi.setLayoutManager(layoutManager);
        recyclerKursi.setItemAnimator(new DefaultItemAnimator());
        recyclerKursi.setAdapter(kursiAdapter);

    }

    @Override
    public void showKursisData() {
        kursiAdapter.notifyDataSetChanged();
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
        Toast.makeText(PilihKursiActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_kursi:
                onBackPressed();
                break;
        }
    }
}
