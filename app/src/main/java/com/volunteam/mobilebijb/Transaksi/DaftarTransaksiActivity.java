package com.volunteam.mobilebijb.Transaksi;

import android.content.Intent;
import android.os.Bundle;
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
import com.volunteam.mobilebijb.Transaksi.pojo.User.GetTransaksiUserResponse;
import com.volunteam.mobilebijb.Transaksi.pojo.User.TransaksiItem;
import com.volunteam.mobilebijb.config.TinyDB;
import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;
import com.volunteam.mobilebijb.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.internal.Internal;
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
                apiToken.getTransaksiUser(MainAPIHelper.key, idUser).enqueue(new Callback<GetTransaksiUserResponse>() {
                    @Override
                    public void onResponse(Call<GetTransaksiUserResponse> call, Response<GetTransaksiUserResponse> response) {
                        if (response.body().getStatusCode() == 1){
                            List<TransaksiItem> transs = response.body().getTransaksi();
                            for (int i=0; i < transs.size(); i++){
                                int jumlah = 0;
                                for (int j=0; j < transs.get(i).getProducts().size(); j++){
                                    jumlah += Integer.valueOf(transs.get(i).getProducts().get(j).getJumlah());
                                }
                                transs.get(i).setJumlah(jumlah);
                            }
                            listTransaksi.addAll(transs);
                            transaksiAdapter.notifyDataSetChanged();
                            if (listTransaksi.size() > 0){
                                layoutEmptyTransaksi.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GetTransaksiUserResponse> call, Throwable t) {
                        Toast.makeText(DaftarTransaksiActivity.this, "Periksa koneksi internet!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }catch (Exception e){

        }

    }
}
