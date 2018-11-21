package com.volunteam.mobilebijb.myflight;

import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyFlightActivityPresenter {

    private List<MyFlight> myFlightList;
    private View view;

    public MyFlightActivityPresenter(View view) {
        this.view = view;
        this.myFlightList = new ArrayList<MyFlight>();
    }

    public void getMyFlightData(String id, String token){
        myFlightList.clear();
        myFlightList.add(new MyFlight());
        view.showFlightsData();
        view.hideRefresh();
    }

    public List<MyFlight> getMyFlightList() {
        return myFlightList;
    }

    public interface View{
        void showFlightsData();
        void showRefresh();
        void hideRefresh();
        void showMessage(String message);
    }
}
