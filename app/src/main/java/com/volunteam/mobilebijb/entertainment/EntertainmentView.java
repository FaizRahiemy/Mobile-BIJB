package com.volunteam.mobilebijb.entertainment;

import com.volunteam.mobilebijb.entertainment.models.news.Article;
import com.volunteam.mobilebijb.entertainment.models.movie.Result;

import java.util.List;

public interface EntertainmentView {
    void setBerita(List<Article> articleList);
    void setHeadlineBerita(Article headlineBerita);
    void onFailureGetBerita(String message);

    void setFilmSortByPopularity(List<Result> filmSortByPopularity);
    void setFilmSortByNew(List<Result> filmSortByNew);

    void onFailureGetFilm(String message);
}
