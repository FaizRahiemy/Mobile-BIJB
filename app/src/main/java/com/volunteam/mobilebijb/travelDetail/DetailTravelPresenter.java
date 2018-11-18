package com.volunteam.mobilebijb.travelDetail;

import android.view.View;

import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTravelPresenter {


    private ResultTravelDetail travelDetails;
    private List<Komentar> komentarList;
    private View view;

    public DetailTravelPresenter(View view) {
        this.view = view;
        this.travelDetails = new ResultTravelDetail();
        this.komentarList = new ArrayList<>();
    }

    public void getTraverDetail(String idTravel){
        view.showProgressBar();
        API api = MainAPIHelper.getClient("").create(API.class);
        Call<TravelDetailResponse> call = api.getTraverDetail(idTravel);
        call.enqueue(new Callback<TravelDetailResponse>() {
            @Override
            public void onResponse(Call<TravelDetailResponse> call, Response<TravelDetailResponse> response) {
                try{
                    if (response.body().getStatusCode().equals("1")) {
                        // valid
                        komentarList.clear();
                        travelDetails = response.body().getResultPost().get(0);
                        komentarList.addAll(travelDetails.getKomentar());
                        view.showTravelDetail(travelDetails);
                        view.hideProgressBar();
                    }else{
                        // not valid
                        view.hideProgressBar();
                        view.showMessage("Error when getting user information");
                    }
                }catch (Exception e){
                    view.hideProgressBar();
                }
            }
            @Override
            public void onFailure(Call<TravelDetailResponse> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    view.hideProgressBar();
                    view.showMessage("Error, please check your internet connection");
                }
            }
        });

    }

    public List<Komentar> getKomentarList() {
        return komentarList;
    }

    public ResultTravelDetail getTravelDetails() {
        return travelDetails;
    }

    public interface View{
        void showTravelDetail(ResultTravelDetail resultTravelDetail);
        void showProgressBar();
        void hideProgressBar();
        void showMessage(String message);
    }
}
