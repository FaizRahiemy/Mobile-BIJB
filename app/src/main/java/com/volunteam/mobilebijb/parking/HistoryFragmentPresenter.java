package com.volunteam.mobilebijb.parking;

import android.view.View;

import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragmentPresenter {

    private ArrayList<Parkir> parkirList;
    private View view;

    public HistoryFragmentPresenter(View view) {
        this.view = view;
        this.parkirList = new ArrayList<Parkir>();
    }

    public void getHistoryParking(String idUser, String token){
        view.showRefresh();
        API api = MainAPIHelper.getClient(token).create(API.class);
        Call<HisrotyResponse> call = api.showParking(idUser);
        call.enqueue(new Callback<HisrotyResponse>() {
            @Override
            public void onResponse(Call<HisrotyResponse> call, Response<HisrotyResponse> response) {
                try{
                    if (response.body().getStatus_code() == 1) {
                        // valid
                        parkirList.clear();
                        ArrayList<Parkir> temp = new ArrayList<Parkir>();
                        for (Result_parking p : response.body().getResult_parking()){
                            if (p.getStatus().equals("0")){
                                parkirList.add(new Parkir(p.getId(), p.getTanggal_masuk(),
                                        p.getTanggal_keluar(), p.getKendaraan(), p.getParkir(),
                                        p.getPlat(), p.getStatus(), p.getDurasi()));
                            }else{
                                temp.add(new Parkir(p.getId(), p.getTanggal_masuk(),
                                        p.getTanggal_keluar(), p.getKendaraan(), p.getParkir(),
                                        p.getPlat(), p.getStatus(), p.getDurasi()));
                            }
                        }
                        parkirList.addAll(temp);
                        view.showHistoryData();
                        view.hideRefresh();
                    }else{
                        // not valid
                        view.hideRefresh();
                        view.showMessage("Error when getting user information");
                    }
                }catch (Exception e){

                }
            }
            @Override
            public void onFailure(Call<HisrotyResponse> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    view.hideRefresh();
                    view.showMessage("Error, please check your internet connection");
                }
            }
        });
    }

    public ArrayList<Parkir> getParkirList() {
        return parkirList;
    }

    public interface View{
        void showHistoryData();
        void showRefresh();
        void hideRefresh();
        void showMessage(String message);
    }
}
