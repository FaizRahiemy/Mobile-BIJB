package com.volunteam.mobilebijb.myflight;

public class Penumpang {
    private String nama;
    private String noKTP;
    private String kategori;
    private String imgKtp;

    public Penumpang(String nama, String noKTP, String kategori, String imgKtp) {
        this.nama = nama;
        this.noKTP = noKTP;
        this.kategori = kategori;
        this.imgKtp = imgKtp;

    }

    public Penumpang() {
        nama = "Iqbal";
        noKTP = "3201890929293";
        kategori = "Dewasa";
        imgKtp = "";
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoKTP() {
        return noKTP;
    }

    public void setNoKTP(String noKTP) {
        this.noKTP = noKTP;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getImgKtp() {
        return imgKtp;
    }

    public void setImgKtp(String imgKtp) {
        this.imgKtp = imgKtp;
    }
}
