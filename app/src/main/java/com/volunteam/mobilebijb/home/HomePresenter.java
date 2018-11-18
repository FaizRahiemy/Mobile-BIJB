package com.volunteam.mobilebijb.home;

import com.volunteam.mobilebijb.config.TinyDB;
import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;
import com.volunteam.mobilebijb.entertainment.models.news.Article;
import com.volunteam.mobilebijb.entertainment.models.news.ResponseNewsApi;
import com.volunteam.mobilebijb.entertainment.models.movie.ResponseNowPlayingApi;
import com.volunteam.mobilebijb.home.model.DummyFlightInfo;
import com.volunteam.mobilebijb.login.LoginResponse;
import com.volunteam.mobilebijb.merchandise.pojo.GetMerchandiseResponse;
import com.volunteam.mobilebijb.merchandise.pojo.MerchsItem;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {
    private HomeView homeView;

    public HomePresenter(HomeView homeView) {
        this.homeView = homeView;
    }

    public void getFlightInfo(){
        List<DummyFlightInfo> dummyFlightInfoList = new ArrayList<>();
        dummyFlightInfoList.add(new DummyFlightInfo("Nov 01 / 14:05 WIB", "Surabaya (SUB/WARR)", "QG834", "Landed"));
        homeView.setFlightInfo(dummyFlightInfoList);
    }

    public void getMerchandise(){
        final API api = MainAPIHelper.getClient("").create(API.class);
        api.getMerchandise(MainAPIHelper.key).enqueue(new Callback<GetMerchandiseResponse>() {
            @Override
            public void onResponse(Call<GetMerchandiseResponse> call, Response<GetMerchandiseResponse> response) {
                List<MerchsItem> merchList = new ArrayList<>();
                if (response.body().getStatusCode() == 1){
                    for (int i = 0; i< response.body().getMerchs().size(); i++){
                        merchList.add(response.body().getMerchs().get(i));
                    }
                    homeView.setMerchandise(merchList);
                }
            }

            @Override
            public void onFailure(Call<GetMerchandiseResponse> call, Throwable t) {
                homeView.onFailureGetMerchandise(t.getMessage());
            }
        });
    }

    public void getNowPlayingMovies(String apiKey){
        System.out.println("get now playing movie..");
        final API api = MainAPIHelper.getFilmClient().create(API.class);
        api.getNowPlayingMovies(apiKey, "en-Us", "1").enqueue(new Callback<ResponseNowPlayingApi>() {
            @Override
            public void onResponse(Call<ResponseNowPlayingApi> call, Response<ResponseNowPlayingApi> response) {
                homeView.setFilmSortByPopularity(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ResponseNowPlayingApi> call, Throwable t) {
                homeView.onFailureGetFilm(t.getMessage());
            }
        });
    }

    public void getBerita(String country, String apiKey){
        final API api = MainAPIHelper.getNewsClient().create(API.class);
        api.getHeadlines(country, apiKey).enqueue(new Callback<ResponseNewsApi>() {
            @Override
            public void onResponse(Call<ResponseNewsApi> call, Response<ResponseNewsApi> response) {
                if (response.body().getStatus().equals("ok")){
                    List<Article> articleList = new ArrayList<>();
                    for (int i = 0; i< 4; i++){
                        articleList.add(response.body().getArticles().get(i));
                    }
                    homeView.setBerita(articleList);
                }
            }

            @Override
            public void onFailure(Call<ResponseNewsApi> call, Throwable t) {
                homeView.onFailureGetBerita(t.getMessage());
            }
        });
    }

    public void getUser(String token, String id){
        API api = MainAPIHelper.getClient(token).create(API.class);
        Call<LoginResponse> call = api.showUser(id);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().getStatus_code() == 1) {
                    // valid
                    homeView.getTxProfile().setText(response.body().getResult_login().getNama());
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
//                    view.hideProgressBar();
//                    view.showMessage("Error, please check your internet connection");
                }
            }
        });
    }
}
