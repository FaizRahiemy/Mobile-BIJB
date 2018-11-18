package com.volunteam.mobilebijb.entertainment.detailMovie;

import android.util.Log;

import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;
import com.volunteam.mobilebijb.entertainment.models.trailer.ResponseTrailerMovieApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMoviePresenter {
    private DetailMovieView detailMovieView;

    public DetailMoviePresenter(DetailMovieView detailMovieView) {
        this.detailMovieView = detailMovieView;
    }

    public void getDetailMovie(String idMovie, String apiKey){
        Log.d("Detail Movie", "...");
        System.out.println("idmovie: "+idMovie);
        System.out.println("api key: "+apiKey);
        final API api = MainAPIHelper.getFilmClient().create(API.class);
        api.getDetailMovie("movie/"+idMovie+"/videos", apiKey).enqueue(new Callback<ResponseTrailerMovieApi>() {
            @Override
            public void onResponse(Call<ResponseTrailerMovieApi> call, Response<ResponseTrailerMovieApi> response) {
                System.out.println("jumlah trailer: "+String.valueOf(response.body().getResults().size()));
                System.out.println("id video: "+response.body().getResults().get(0).getKey());
                detailMovieView.setDetailMovie(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ResponseTrailerMovieApi> call, Throwable t) {
                detailMovieView.onFailedGetDetailMovie(t.getMessage());
            }
        });

    }
}
