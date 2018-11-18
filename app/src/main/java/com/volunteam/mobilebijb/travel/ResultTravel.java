package com.volunteam.mobilebijb.travel;

import java.util.Comparator;

/**
 * Created by iqbal on 5/10/2018.
 */

public class ResultTravel {
    private int id, kategori, komentar, harga;
    private String nama, description, image, address, tanggal;
    private float score, lat, lon;
    private double radius;

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getKomentar() {
        return komentar;
    }

    public void setKomentar(int komentar) {
        this.komentar = komentar;
    }

    public static Comparator<ResultTravel> getCompareRadius() {
        return compareRadius;
    }

    public static void setCompareRadius(Comparator<ResultTravel> compareRadius) {
        ResultTravel.compareRadius = compareRadius;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKategori() {
        return kategori;
    }

    public void setKategori(int kategori) {
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public static Comparator<ResultTravel> compareRadius = new Comparator<ResultTravel>() {

        public int compare(ResultTravel s1, ResultTravel s2) {

            double rad1 = s1.getRadius();
            double rad2 = s2.getRadius();

            /*For ascending order*/
            return Double.compare(rad1, rad2);

            /*For descending order*/
//			return rad2-rad1;
        }};
}
