package com.volunteam.mobilebijb.home.model;

public class DummyFlightInfo {
    private String schedule;
    private String airlines;
    private String flightNo;
    private String status;
    private String imgAirlines;

    public DummyFlightInfo(String schedule, String airlines, String flightNo, String status) {
        this.schedule = schedule;
        this.airlines = airlines;
        this.flightNo = flightNo;
        this.status = status;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getAirlines() {
        return airlines;
    }

    public void setAirlines(String airlines) {
        this.airlines = airlines;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImgAirlines() {
        return imgAirlines;
    }

    public void setImgAirlines(String imgAirlines) {
        this.imgAirlines = imgAirlines;
    }
}
