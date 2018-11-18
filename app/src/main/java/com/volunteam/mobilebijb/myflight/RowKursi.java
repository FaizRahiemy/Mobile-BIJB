package com.volunteam.mobilebijb.myflight;

public class RowKursi {

    Kursi kursis [] = new Kursi[6];

    public RowKursi() {
        for (int i=0;i<6;i++){
            kursis[i] = new Kursi(String.valueOf(i));
        }
    }

    public Kursi[] getKursis() {
        return kursis;
    }

    public void setKursis(Kursi[] kursis) {
        this.kursis = kursis;
    }
}
