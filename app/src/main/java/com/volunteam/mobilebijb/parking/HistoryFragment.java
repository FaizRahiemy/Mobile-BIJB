package com.volunteam.mobilebijb.parking;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.config.TinyDB;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment implements HistoryFragmentPresenter.View{

    private RecyclerView recyclerParking;
    private HistoryParkingAdapter historyParkingAdapter;
    private SwipeRefreshLayout swipeContainer;
    private HistoryFragmentPresenter presenter;
    private LinearLayout layoutEmpty;
    private TinyDB tinyDB;
    private String token = "";
    private String id = "";

    public HistoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history_parking, container, false);

        // get id and token from sharedpreference
        tinyDB = new TinyDB(getContext());
        try{
            token = tinyDB.getString("token");
            id = tinyDB.getString("id");
        }catch (Exception e){
            Toast.makeText(getContext(),"There is an error, please re-login",
                    Toast.LENGTH_SHORT).show();
        }
        presenter = new HistoryFragmentPresenter(this);

        // init views
        defineViews(v);

        presenter.getHistoryParking(id,token);

        return v;
    }

    private void defineViews(View v){
        recyclerParking = (RecyclerView)v.findViewById(R.id.recycler_parking);
        layoutEmpty = (LinearLayout)v.findViewById(R.id.layout_empty_history);
        swipeContainer = (SwipeRefreshLayout)v.findViewById(R.id.swipe_parking);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getHistoryParking(id,token);
            }
        });
        swipeContainer.setColorSchemeColors(Color.parseColor("#005c97"),
                Color.parseColor("#363759"));

        historyParkingAdapter = new HistoryParkingAdapter(presenter.getParkirList());
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerParking.setLayoutManager(layoutManager);
        recyclerParking.setItemAnimator(new DefaultItemAnimator());
        recyclerParking.setAdapter(historyParkingAdapter);

        if (presenter.getParkirList().size()<1){
            layoutEmpty.setVisibility(View.INVISIBLE);
        }else{
            layoutEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showHistoryData() {
        historyParkingAdapter.notifyDataSetChanged();
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
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }
}
