package com.volunteam.mobilebijb.entertainment.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.smarteist.autoimageslider.SliderLayout;

import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.entertainment.EntertainmentPresenter;
import com.volunteam.mobilebijb.entertainment.adapters.MovieAdapter;
import com.volunteam.mobilebijb.entertainment.models.movie.Result;

import java.util.List;

public class MovieFragment extends Fragment {

    private SwipeRefreshLayout swipe_layout;
    private SliderLayout auto_image_slider;
    private TextView btn_lihat_semua_popular, btn_lihat_semua_new;
    private RecyclerView recycler_popular, recycler_new;

    private EntertainmentPresenter entertainmentPresenter;

    public MovieFragment() {
    }

    public void setEntertainmentPresenter(EntertainmentPresenter entertainmentPresenter) {
        this.entertainmentPresenter = entertainmentPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipe_layout = view.findViewById(R.id.swipe_layout);
        auto_image_slider = view.findViewById(R.id.auto_image_slider);
        btn_lihat_semua_popular = view.findViewById(R.id.btn_lihat_semua_popular);
        btn_lihat_semua_new = view.findViewById(R.id.btn_lihat_semua_new);
        recycler_popular = view.findViewById(R.id.recycler_popular);
        recycler_new = view.findViewById(R.id.recycler_new);

        onSwipeRefresh(swipe_layout);
    }

    public void onSwipeRefresh(SwipeRefreshLayout swipe_layout){
        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                entertainmentPresenter.getPopularMovies("4b9bfb0e83de2a4afb17c157ccb254f3");
                entertainmentPresenter.getNowPlayingMovies("4b9bfb0e83de2a4afb17c157ccb254f3");
            }
        });
    }

    public void stopRefresh(){
        if (swipe_layout.isRefreshing()){
            swipe_layout.setRefreshing(false);
        }
    }

    public void setAdapterMoviePopular(List<Result> listMoviePopular){
        MovieAdapter moviePopularAdapter = new MovieAdapter(listMoviePopular);
        RecyclerView.LayoutManager layoutManagerOption = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_popular.setLayoutManager(layoutManagerOption);
        recycler_popular.setItemAnimator(new DefaultItemAnimator());
        recycler_popular.setAdapter(moviePopularAdapter);
        System.out.println("jumlah movie popular: "+String.valueOf(moviePopularAdapter.getItemCount()));
    }

    public void setAdapterMovieNew(List<Result> listMovieNew){
        MovieAdapter movieNewAdapter = new MovieAdapter(listMovieNew);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 4,LinearLayoutManager.VERTICAL, false);
        recycler_new.setLayoutManager(layoutManager);
        recycler_new.setItemAnimator(new DefaultItemAnimator());
        recycler_new.setAdapter(movieNewAdapter);
        System.out.println("jumlah movie new: "+String.valueOf(movieNewAdapter.getItemCount()));
    }
}
