package com.volunteam.mobilebijb.myflight;

import android.view.View;

public class AddFlightActivityPresenter {

    private View view;

    public AddFlightActivityPresenter(View view) {
        this.view = view;
    }

    public void addFlightData(String id, String token, String kode, String ktp){
        view.clearField();
    }

    public interface View{
        void clearField();
        void showProgressBar();
        void hideProgressBar();
        void showMessage(String message);
    }
}
