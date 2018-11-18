package com.volunteam.mobilebijb.home;

import android.widget.TextView;

import com.volunteam.mobilebijb.entertainment.models.news.Article;
import com.volunteam.mobilebijb.entertainment.models.movie.Result;
import com.volunteam.mobilebijb.home.model.DummyFlightInfo;
import com.volunteam.mobilebijb.merchandise.pojo.MerchsItem;

import java.util.List;

public interface HomeView {
    void setFlightInfo(List<DummyFlightInfo> dummyFlightInfoList);

    void setMerchandise(List<MerchsItem> merchsList);

    void setBerita(List<Article> articleList);

    void setFilmSortByPopularity(List<Result> filmSortByPopularity);

    void getUser(String token, String id);

    void onFailureGetFilm(String message);
    void onFailureGetBerita(String message);
    void onFailureGetMerchandise(String message);

    TextView getTxProfile();
}
