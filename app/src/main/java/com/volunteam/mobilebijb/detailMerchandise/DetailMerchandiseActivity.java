package com.volunteam.mobilebijb.detailMerchandise;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.Transaksi.pojo.TambahCartResponse;
import com.volunteam.mobilebijb.config.TinyDB;
import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;
import com.volunteam.mobilebijb.detailMerchandise.pojo.GetMerchandiseDetailResponse;
import com.volunteam.mobilebijb.detailMerchandise.pojo.Merchs;
import com.volunteam.mobilebijb.home.HomeActivity;
import com.volunteam.mobilebijb.login.LoginActivity;
import com.volunteam.mobilebijb.profile.ProfileActivity;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMerchandiseActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView img_produk;
    private TextView txt_nama_produk;
    private TextView txt_harga_produk;
    private TextView txt_deskripsi_produk;
    private RelativeLayout btn_tambah_keranjang;

    String id;
    Merchs item;
    API api;
    API apiToken;
    TinyDB tinyDB;
    String token;
    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_merchandise);

        defineViews();
        try {
            tinyDB = new TinyDB(this);
            token = tinyDB.getString("token");
            idUser = tinyDB.getString("id");

            apiToken = MainAPIHelper.getClient(token).create(API.class);

        }catch (Exception e){

        }
        api = MainAPIHelper.getClient("").create(API.class);
        id = getIntent().getStringExtra("id");
        getProductInfo();
    }

    private void defineViews(){
        //toolbar
        toolbar = findViewById(R.id.toolbar);

        //detil produk
        img_produk = findViewById(R.id.img_produk);
        txt_nama_produk = findViewById(R.id.txt_nama_produk);
        txt_harga_produk = findViewById(R.id.txt_harga_produk);
        txt_deskripsi_produk = findViewById(R.id.txt_deskripsi_produk);

        //tambah keranjang
        btn_tambah_keranjang = findViewById(R.id.btn_tambah_keranjang);
        btn_tambah_keranjang.setOnClickListener(this);
    }

    private void getProductInfo(){
        final Dialog mdialog=new Dialog(this);
        mdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mdialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        mdialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        mdialog.getWindow ().setBackgroundDrawableResource (android.R.color.transparent);
        mdialog.show();

        api.getMerchandiseDetail(MainAPIHelper.key, id).enqueue(new Callback<GetMerchandiseDetailResponse>() {
            @Override
            public void onResponse(Call<GetMerchandiseDetailResponse> call, Response<GetMerchandiseDetailResponse> response) {
                if (response.body().getStatusCode() == 1){
                    item = response.body().getMerchs();
                    setToolbar(toolbar);
                    setDetailProduct();
                    mdialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetMerchandiseDetailResponse> call, Throwable t) {
                Toast.makeText(DetailMerchandiseActivity.this, "Periksa koneksi internet anda!", Toast.LENGTH_SHORT).show();
                mdialog.dismiss();
            }
        });
    }

    private void setToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(item.getName());
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setDetailProduct(){
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);

        txt_nama_produk.setText(item.getName());
        txt_harga_produk.setText("Rp. " + formatter.format(Float.valueOf(item.getHarga())));
        Spanned sp = Html.fromHtml(item.getDescription().replace("&lt;", "<").replace("&gt;", ">"));
        txt_deskripsi_produk.setText(sp);
        if (item.getImage().isEmpty()){
            Picasso.with(getApplicationContext())
                    .load(R.color.colorPrimaryDark)
                    .error(android.R.color.transparent)
                    .into(img_produk);
        }else{
            Picasso.with(getApplicationContext())
                    .load(item.getImage())
                    .placeholder(R.color.colorPrimaryDark)
                    .error(android.R.color.transparent)
                    .centerCrop()
                    .fit()
                    .into(img_produk);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_tambah_keranjang:
                if (token.equals("")&&idUser.equals("")){
                    Intent intent = new Intent(DetailMerchandiseActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    apiToken.tambahCart(MainAPIHelper.key, idUser, id).enqueue(new Callback<TambahCartResponse>() {
                        @Override
                        public void onResponse(Call<TambahCartResponse> call, Response<TambahCartResponse> response) {
                            Toast.makeText(DetailMerchandiseActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TambahCartResponse> call, Throwable t) {
                            Toast.makeText(DetailMerchandiseActivity.this, "Periksa koneksi internet!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
        }
    }
}
