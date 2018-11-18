package com.volunteam.mobilebijb.parking;

public class Result_parking {

    private String id;
    private String id_user;
    private String tanggal_masuk;
    private String tanggal_keluar;
    private String durasi;
    private String kendaraan;
    private String parkir;
    private String plat;
    private String status;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getId_user() {
        return id_user;
    }

    public String getTanggal_masuk() {
        return tanggal_masuk;
    }

    public String getTanggal_keluar() {
        return tanggal_keluar;
    }

    public String getDurasi() {
        return durasi;
    }

    public String getKendaraan() {
        return kendaraan;
    }

    public String getParkir() {
        return parkir;
    }

    public String getPlat() {
        return plat;
    }

    public String getStatus() {
        return status;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public void setTanggal_masuk(String tanggal_masuk) {
        this.tanggal_masuk = tanggal_masuk;
    }

    public void setTanggal_keluar(String tanggal_keluar) {
        this.tanggal_keluar = tanggal_keluar;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public void setKendaraan(String kendaraan) {
        this.kendaraan = kendaraan;
    }

    public void setParkir(String parkir) {
        this.parkir = parkir;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
