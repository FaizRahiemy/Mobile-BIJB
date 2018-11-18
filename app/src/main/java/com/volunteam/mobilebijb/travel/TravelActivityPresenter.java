package com.volunteam.mobilebijb.travel;

import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TravelActivityPresenter {

    private List<ResultTravel> resultTravels;
    private View view;

    public TravelActivityPresenter(View view) {
        this.resultTravels = new ArrayList<>();
        this.view = view;
    }

    public void getTravelData(){
        // TODO integrate with API and implement progressbar
        view.showRefresh();
        API api = MainAPIHelper.getClient("").create(API.class);
        Call<TravelResponse> call = api.showTravel("");
        call.enqueue(new Callback<TravelResponse>() {
            @Override
            public void onResponse(Call<TravelResponse> call, Response<TravelResponse> response) {
                try{
                    if (response.body().getStatusCode().equals("1")) {
                        // valid
                        resultTravels.clear();
                        resultTravels.addAll(response.body().getResultPost());
                        view.showTravelData();
                        view.hideRefresh();
                    }else{
                        // not valid
                        view.hideRefresh();
                        view.showMessage("Error when getting user information");
                    }
                }catch (Exception e){
                    view.hideRefresh();
                }
            }
            @Override
            public void onFailure(Call<TravelResponse> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    view.hideRefresh();
                    view.showMessage("Error, please check your internet connection");
                }
            }
        });
    }

    public List<ResultTravel> getResultTravels() {
        return resultTravels;
    }

    public interface View{
        void showTravelData();
        void showRefresh();
        void hideRefresh();
        void showMessage(String message);
    }
}
