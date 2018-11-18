package com.volunteam.mobilebijb.Travelling.PublicTransport;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.Travelling.PublicTransport.pojo.Public.PublicTransportResponse;
import com.volunteam.mobilebijb.Travelling.PublicTransport.pojo.Public.TransportItem;
import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicTransportActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_rute)
    Toolbar toolbarRute;
    @BindView(R.id.item_public_transport)
    RecyclerView itemPublicTransport;

    API api;
    PublicTransportAdapter publicTransportAdapter;
    List<TransportItem> listTransport = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_transport);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarRute);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarRute.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        api = MainAPIHelper.getClient("").create(API.class);

        final Dialog mdialog=new Dialog(this);
        mdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mdialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        mdialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        mdialog.getWindow ().setBackgroundDrawableResource (android.R.color.transparent);
        mdialog.show();

        publicTransportAdapter = new PublicTransportAdapter(listTransport);
        RecyclerView.LayoutManager layoutManagerProduct = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        itemPublicTransport.setLayoutManager(layoutManagerProduct);
        itemPublicTransport.setItemAnimator(new DefaultItemAnimator());
        itemPublicTransport.setAdapter(publicTransportAdapter);

        Call<PublicTransportResponse> callTransport = api.getPublicTransport(MainAPIHelper.key);
        callTransport.enqueue(new Callback<PublicTransportResponse>() {
            @Override
            public void onResponse(Call<PublicTransportResponse> callTransport, Response<PublicTransportResponse> response) {
                if (response.body().getStatusCode() == 1) {
                    listTransport.addAll(response.body().getTransport());
                    publicTransportAdapter.notifyDataSetChanged();
                }
                mdialog.dismiss();
            }

            @Override
            public void onFailure(Call<PublicTransportResponse> callTransport, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(PublicTransportActivity.this, "Harap periksa koneksi internet", Toast.LENGTH_SHORT).show();
                }
                mdialog.dismiss();
            }
        });
    }
}
