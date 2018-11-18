package com.volunteam.mobilebijb.parking;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;
import com.volunteam.mobilebijb.register.InsertResponse;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookingFragmentPresenter {

    private Parkir parkir;
    private View view;

    public BookingFragmentPresenter(View view) {
        this.view = view;
        this.parkir = new Parkir();
    }

    public void addBooking(String idUser, String token, Parkir parkir){
        view.showProgressBar();
        API api = MainAPIHelper.getClient(token).create(API.class);
        Call<InsertResponse> call = api.bookParking(
                idUser,parkir.getTglMParkir(),parkir.getKendaraan(),parkir.getParkir(),
                parkir.getPlat()
        );
        call.enqueue(new Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                try{
                    if (response.body().getStatus_code().equals("1")) {
                        // valid
                        view.showMessage("Booking successful");
                        view.clearField();
                        view.hideProgressBar();
                    }else{
                        // not valid
                        view.hideProgressBar();
                        view.showMessage("Error when updating user information");
                    }
                }catch (Exception e){

                }
            }
            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    view.hideProgressBar();
                    view.showMessage("Error, please check your internet connection");
                }
            }
        });
    }

    public interface View{
        void showProgressBar();
        void hideProgressBar();
        void showTanggalDialog();
        void showWaktuDialog();
        void showMessage(String message);
        void clearField();
    }
}
