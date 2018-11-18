package com.volunteam.mobilebijb.Transaksi;

import android.content.Intent;
import android.os.Bundle;
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
import com.volunteam.mobilebijb.Transaksi.adapter.TransaksiAdapter;
import com.volunteam.mobilebijb.Transaksi.pojo.GetTransaksiResponse;
import com.volunteam.mobilebijb.Transaksi.pojo.TambahCartResponse;
import com.volunteam.mobilebijb.Transaksi.pojo.TransaksiItem;
import com.volunteam.mobilebijb.config.TinyDB;
import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;
import com.volunteam.mobilebijb.detailMerchandise.DetailMerchandiseActivity;
import com.volunteam.mobilebijb.detailMerchandise.pojo.Merchs;
import com.volunteam.mobilebijb.login.LoginActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarTransaksiActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_menu)
    Toolbar toolbarMenu;
    @BindView(R.id.recycler_daftar_transaksi)
    RecyclerView recyclerDaftarTransaksi;
    @BindView(R.id.swipe_daftar_transaksi)
    SwipeRefreshLayout swipeDaftarTransaksi;
    @BindView(R.id.layout_empty_transaksi)
    LinearLayout layoutEmptyTransaksi;

    String id;
    API apiToken;
    TinyDB tinyDB;
    String token;
    String idUser;

    ArrayList<TransaksiItem> listTransaksi = new ArrayList<>();
    TransaksiAdapter transaksiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_transaksi);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarMenu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarMenu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        transaksiAdapter = new TransaksiAdapter(listTransaksi);
        RecyclerView.LayoutManager layoutManagerOption = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerDaftarTransaksi.setLayoutManager(layoutManagerOption);
        recyclerDaftarTransaksi.setItemAnimator(new DefaultItemAnimator());
        recyclerDaftarTransaksi.setAdapter(transaksiAdapter);

        tinyDB = new TinyDB(this);
        token = tinyDB.getString("token");

        apiToken = MainAPIHelper.getClient(token).create(API.class);

        try{
            tinyDB = new TinyDB(this);
            token = tinyDB.getString("token");
            idUser = tinyDB.getString("id");
            if (token.equals("")&&id.equals("")){
                Intent intent = new Intent(DaftarTransaksiActivity.this, LoginActivity.class);
                startActivity(intent);
            }else{
                apiToken.getTransaksiUser(MainAPIHelper.key, idUser).enqueue(new Callback<GetTransaksiResponse>() {
                    @Override
                    public void onResponse(Call<GetTransaksiResponse> call, Response<GetTransaksiResponse> response) {
                        if (response.body().getStatusCode() == 1){
                            listTransaksi.addAll(response.body().getTransaksi());
                            transaksiAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetTransaksiResponse> call, Throwable t) {
                        Toast.makeText(DaftarTransaksiActivity.this, "Periksa koneksi internet!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }catch (Exception e){

        }

    }
}
