package com.volunteam.mobilebijb.myflight;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PilihKursiActivityPresenter {

    private List<RowKursi> rowKursiList;
    private View view;

    public PilihKursiActivityPresenter(View view) {
        this.view = view;
        this.rowKursiList = new ArrayList<>();
    }

    public void getKursiData(String idFlight){
        // TODO integrate with api and impelemnt progressbar
        rowKursiList.clear();
        rowKursiList.add(new RowKursi());
        rowKursiList.add(new RowKursi());
        rowKursiList.add(new RowKursi());
        rowKursiList.add(new RowKursi());
        rowKursiList.add(new RowKursi());
        view.showKursisData();
    }

    public List<RowKursi> getRowKursiList() {
        return rowKursiList;
    }

    public interface View{
        void showKursisData();
        void showRefresh();
        void hideRefresh();
        void showMessage(String message);
    }
}
