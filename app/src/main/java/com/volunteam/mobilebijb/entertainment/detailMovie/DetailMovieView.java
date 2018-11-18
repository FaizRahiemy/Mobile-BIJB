package com.volunteam.mobilebijb.entertainment.detailMovie;

import com.volunteam.mobilebijb.entertainment.models.trailer.Result;

import java.util.List;

public interface DetailMovieView {
    void setDetailMovie(List<Result> resultList);
    void onFailedGetDetailMovie(String message);
}
