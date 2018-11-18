package com.volunteam.mobilebijb.parking;

public class Parkir {

    private String idParkir;
    private String tglMParkir;
    private String tglKParkir;
    private String kendaraan;
    private String parkir;
    private String plat;
    private String status;
    private String durasi;

    public Parkir(String tglMParkir, String kendaraan, String parkir, String plat) {
        this.tglMParkir = tglMParkir;
        this.kendaraan = kendaraan;
        this.parkir = parkir;
        this.plat = plat;
    }

    public Parkir(String idParkir, String tglMParkir, String tglKParkir, String kendaraan, String parkir, String plat, String status, String durasi) {
        this.idParkir = idParkir;
        this.tglMParkir = tglMParkir;
        this.tglKParkir = tglKParkir;
        this.kendaraan = kendaraan;
        this.parkir = parkir;
        this.plat = plat;
        this.status = status;
        this.durasi = durasi;
    }

    public Parkir(String tglMParkir, String tglKParkir, String kendaraan, String parkir, String plat, String status, String durasi) {
        this.tglMParkir = tglMParkir;
        this.tglKParkir = tglKParkir;
        this.kendaraan = kendaraan;
        this.parkir = parkir;
        this.plat = plat;
        this.status = status;
        this.durasi = durasi;
    }

    public Parkir() {
        this.tglMParkir = "";
        this.kendaraan = "";
        this.parkir = "";
        this.plat = "";
        this.status = "";
        this.durasi = "";
    }

    public String getTglMParkir() {
        return tglMParkir;
    }

    public void setTglMParkir(String tglMParkir) {
        this.tglMParkir = tglMParkir;
    }

    public String getKendaraan() {
        return kendaraan;
    }

    public void setKendaraan(String kendaraan) {
        this.kendaraan = kendaraan;
    }

    public String getParkir() {
        return parkir;
    }

    public void setParkir(String parkir) {
        this.parkir = parkir;
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public String getTglKParkir() {
        return tglKParkir;
    }

    public void setTglKParkir(String tglKParkir) {
        this.tglKParkir = tglKParkir;
    }
}
