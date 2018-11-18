package com.volunteam.mobilebijb.myflight;

import java.util.List;

public class MyFlight {
    private String id;
    private String id_user;
    private String kode;
    private String ktp;
    private String from;
    private String to;
    private String tgl_takeoff;
    private String tgl_departure;
    private String tgl_arrival;
    private String time_departure;
    private String time_arrival;
    private String class_flight;
    private String passenger;
    private String status;
    private String airline;
    private String imgAirline;

    private List<Penumpang> penumpangList;

    public MyFlight(String id, String id_user, String kode, String ktp, String from, String to, String tgl_takeoff, String tgl_departure, String tgl_arrival, String time_departure, String time_arrival, String class_flight, String passenger, String status) {
        this.id = id;
        this.id_user = id_user;
        this.kode = kode;
        this.ktp = ktp;
        this.from = from;
        this.to = to;
        this.tgl_takeoff = tgl_takeoff;
        this.tgl_departure = tgl_departure;
        this.tgl_arrival = tgl_arrival;
        this.time_departure = time_departure;
        this.time_arrival = time_arrival;
        this.class_flight = class_flight;
        this.passenger = passenger;
        this.status = status;
    }

    public MyFlight() {
        this.id = "";
        this.id_user = "";
        this.kode = "";
        this.ktp = "";
        this.from = "";
        this.to = "";
        this.tgl_takeoff = "";
        this.tgl_departure = "";
        this.tgl_arrival = "";
        this.time_departure = "";
        this.time_arrival = "";
        this.class_flight = "";
        this.passenger = "";
        this.status = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTgl_takeoff() {
        return tgl_takeoff;
    }

    public void setTgl_takeoff(String tgl_takeoff) {
        this.tgl_takeoff = tgl_takeoff;
    }

    public String getTgl_departure() {
        return tgl_departure;
    }

    public void setTgl_departure(String tgl_departure) {
        this.tgl_departure = tgl_departure;
    }

    public String getTgl_arrival() {
        return tgl_arrival;
    }

    public void setTgl_arrival(String tgl_arrival) {
        this.tgl_arrival = tgl_arrival;
    }

    public String getTime_departure() {
        return time_departure;
    }

    public void setTime_departure(String time_departure) {
        this.time_departure = time_departure;
    }

    public String getTime_arrival() {
        return time_arrival;
    }

    public void setTime_arrival(String time_arrival) {
        this.time_arrival = time_arrival;
    }

    public String getClass_flight() {
        return class_flight;
    }

    public void setClass_flight(String class_flight) {
        this.class_flight = class_flight;
    }

    public String getPassenger() {
        return passenger;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getImgAirline() {
        return imgAirline;
    }

    public void setImgAirline(String imgAirline) {
        this.imgAirline = imgAirline;
    }
}
