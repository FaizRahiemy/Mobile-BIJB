package com.volunteam.mobilebijb.merchandise;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.merchandise.adapter.MerchandiseAdapter;
import com.volunteam.mobilebijb.merchandise.pojo.MerchsItem;

import java.util.List;


public class MerchandiseActivity extends AppCompatActivity implements MerchandiseView{

    //merchandise
    private Toolbar toolbar;
    private SwipeRefreshLayout refresh_layout;
    private RecyclerView recycler_marchendise;

    //presenter
    MerchandisePresenter merchandisePresenter = new MerchandisePresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_souvenier);

        defineViews();
        setToolbar(toolbar);
        merchandisePresenter.getMerchandise();
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                merchandisePresenter.getMerchandise();
            }
        });
    }

    private void defineViews(){
        toolbar = findViewById(R.id.toolbar);
        refresh_layout = findViewById(R.id.refresh_layout);
        recycler_marchendise = findViewById(R.id.recycler_marchendise);
    }

    private void setToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Souvenier");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void setMerchandise(List<MerchsItem> merchsList) {
        MerchandiseAdapter merchandiseAdapter = new MerchandiseAdapter(merchsList, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        recycler_marchendise.setLayoutManager(layoutManager);
        recycler_marchendise.setItemAnimator(new DefaultItemAnimator());
        recycler_marchendise.setAdapter(merchandiseAdapter);
        refresh_layout.setRefreshing(false);
    }

    @Override
    public void onFailureGetMerchandise(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
