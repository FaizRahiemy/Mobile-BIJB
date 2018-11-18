package com.volunteam.mobilebijb.Travelling.TravellingActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.Travelling.PublicTransport.PublicTransportAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TravellingActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_traveling)
    Toolbar toolbarTraveling;
    @BindView(R.id.item_traveling)
    RecyclerView itemTraveling;

    TravellingItemAdapter travelingAdapter;
    List<TravellingItemModel> listTravel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelling);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarTraveling);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTraveling.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        travelingAdapter = new TravellingItemAdapter(listTravel);
        RecyclerView.LayoutManager layoutManagerProduct = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        itemTraveling.setLayoutManager(layoutManagerProduct);
        itemTraveling.setItemAnimator(new DefaultItemAnimator());
        itemTraveling.setAdapter(travelingAdapter);
        generateTraveling();
    }

    private void generateTraveling(){
        listTravel.add(new TravellingItemModel(1, "Rute Ke Bandara", "https://static.inilah.com/data/berita/foto/2446203.jpg"));
        listTravel.add(new TravellingItemModel(2, "Wisata", "https://tempatwisataseru.com/wp-content/uploads/2015/10/Wisata-Alam-Maribaya-di-Bandung-Utara.jpg"));
        listTravel.add(new TravellingItemModel(3, "Public Transport", "https://s2.bukalapak.com/img/7573249531/w-1000/Simulator_BUS_Indonesia_ETS2_v123_Full_DLC.png"));
        travelingAdapter.notifyDataSetChanged();
    }
}
