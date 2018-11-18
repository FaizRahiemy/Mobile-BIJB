package com.volunteam.mobilebijb.entertainment;

import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;
import com.volunteam.mobilebijb.entertainment.models.news.Article;
import com.volunteam.mobilebijb.entertainment.models.news.ResponseNewsApi;
import com.volunteam.mobilebijb.entertainment.models.movie.ResponseNowPlayingApi;
import com.volunteam.mobilebijb.entertainment.models.movie.ResponsePopularMovieApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntertainmentPresenter {
    private EntertainmentView entertainmentView;

    public EntertainmentPresenter(EntertainmentView entertainmentView) {
        this.entertainmentView = entertainmentView;
    }

    public void getBerita(String country, String apiKey){
        final API api = MainAPIHelper.getNewsClient().create(API.class);
        api.getHeadlines(country, apiKey).enqueue(new Callback<ResponseNewsApi>() {
            @Override
            public void onResponse(Call<ResponseNewsApi> call, Response<ResponseNewsApi> response) {
                if (response.body().getStatus().equals("ok")){
                    List<Article> articleList = new ArrayList<>();
                    for (int i = 0; i < response.body().getArticles().size(); i++){
                        if (i == 0){
                            entertainmentView.setHeadlineBerita(response.body().getArticles().get(i));
                        }else {
                            articleList.add(response.body().getArticles().get(i));
                        }
                        entertainmentView.setBerita(articleList);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseNewsApi> call, Throwable t) {
                entertainmentView.onFailureGetBerita(t.getMessage());
            }
        });
    }

    public void getNowPlayingMovies(String apiKey){
        System.out.println("get now playing movie..");
        final API api = MainAPIHelper.getFilmClient().create(API.class);
        api.getNowPlayingMovies(apiKey, "en-Us", "1").enqueue(new Callback<ResponseNowPlayingApi>() {
            @Override
            public void onResponse(Call<ResponseNowPlayingApi> call, Response<ResponseNowPlayingApi> response) {
                System.out.println("jumlah film now playing: "+String.valueOf(response.body().getResults()));
                entertainmentView.setFilmSortByPopularity(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ResponseNowPlayingApi> call, Throwable t) {
                entertainmentView.onFailureGetFilm(t.getMessage());
            }
        });
    }

    public void getPopularMovies(String apiKey){
        System.out.println("get popular movie..");
        final API api = MainAPIHelper.getFilmClient().create(API.class);
        api.getPopularMovies(apiKey, "en-Us", "1").enqueue(new Callback<ResponsePopularMovieApi>() {
            @Override
            public void onResponse(Call<ResponsePopularMovieApi> call, Response<ResponsePopularMovieApi> response) {
                System.out.println("jumlah film popular: "+String.valueOf(response.body().getResults()));
                entertainmentView.setFilmSortByNew(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ResponsePopularMovieApi> call, Throwable t) {
                entertainmentView.onFailureGetFilm(t.getMessage());
            }
        });
    }
}
