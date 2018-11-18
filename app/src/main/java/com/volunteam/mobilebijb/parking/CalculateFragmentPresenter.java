package com.volunteam.mobilebijb.parking;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;

public class CalculateFragmentPresenter {

    private Parkir parkir;
    private View view;

    public CalculateFragmentPresenter(View view) {
        this.parkir = new Parkir();
        this.view = view;
    }

    public void calculateParking(HashMap<String,String> paramPlan){
        String kendaraan = paramPlan.get("kendaraan");
        String tglMasuk = paramPlan.get("tgl_masuk");
        String jamMasuk = paramPlan.get("jam_masuk");
        String tglKeluar = paramPlan.get("tgl_keluar");
        String jamKeluar = paramPlan.get("jam_keluar");
        String biaya = "1000";
        view.showCost("Rp. "+toStringNoDecimal(biaya));
    }

    public interface View{
        void showCost(String cost);
        void showProgressBar();
        void hideProgressBar();
    }

    private String toStringNoDecimal(String value) {
        try {
            Double dValue = Double.parseDouble(value);
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            formatter .applyPattern("#,###");
            return formatter.format(dValue).replace(",",".");
        }catch (Exception e){
            return value;
        }
    }
}