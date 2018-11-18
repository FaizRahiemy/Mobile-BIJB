package com.volunteam.mobilebijb.Travelling.PublicTransport;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.SliderLayout;
import com.squareup.picasso.Picasso;
import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.Travelling.PublicTransport.pojo.detail.Transport;
import com.volunteam.mobilebijb.Travelling.PublicTransport.pojo.detail.TransportDetailResponse;
import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransportDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_transport_detail)
    Toolbar toolbarTransportDetail;
    @BindView(R.id.collapsing_toolbar_transport)
    CollapsingToolbarLayout collapsingToolbarTransport;
    @BindView(R.id.tx_desc_transport)
    TextView txDescTransport;
    @BindView(R.id.scroll_post)
    NestedScrollView scrollPost;
    @BindView(R.id.appbar_transport)
    AppBarLayout appbarTransport;
    @BindView(R.id.img_transport_detail)
    ImageView imgTransportDetail;

    private SliderLayout sliderEvent;
    private String id;
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarTransportDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTransportDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        collapsingToolbarTransport.setTitle("Transport");
        id = getIntent().getStringExtra("id");

        appbarTransport.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
                } else if (isShow) {
                    //collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        api = MainAPIHelper.getClient("").create(API.class);

        final Dialog mdialog=new Dialog(this);
        mdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mdialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        mdialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        mdialog.getWindow ().setBackgroundDrawableResource (android.R.color.transparent);
        mdialog.show();

        Call<TransportDetailResponse> call = api.getTransportDetail(MainAPIHelper.key, id);
        call.enqueue(new Callback<TransportDetailResponse>() {
            @Override
            public void onResponse(Call<TransportDetailResponse> call, Response<TransportDetailResponse> response) {
                if (response.body().getStatusCode() == 1) {
                    Transport trans = response.body().getTransport();
                    Spanned sp = Html.fromHtml(trans.getDescription().replace("&lt;", "<").replace("&gt;", ">"));

                    txDescTransport.setText(sp);
                    collapsingToolbarTransport.setTitle(trans.getName());
                    if (trans.getImages().isEmpty()){
                        Picasso.with(TransportDetailActivity.this)
                                .load(R.color.colorPrimaryDark)
                                .error(android.R.color.transparent)
                                .into(imgTransportDetail);
                    }else{
                        Picasso.with(TransportDetailActivity.this)
                                .load(trans.getImages())
                                .fit()
                                .placeholder(R.color.colorPrimaryDark)
                                .error(android.R.color.transparent)
                                .into(imgTransportDetail);
                    }
                } else {
                    Toast.makeText(TransportDetailActivity.this, "Gagal menampilkan item", Toast.LENGTH_SHORT).show();
                }
                mdialog.dismiss();
            }

            @Override
            public void onFailure(Call<TransportDetailResponse> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(TransportDetailActivity.this, "Harap periksa koneksi internet", Toast.LENGTH_SHORT).show();
                }
                mdialog.dismiss();
            }
        });
    }
}
