package com.volunteam.mobilebijb.myflight;

public class Kursi {

    private String row;
    private String column;
    private String status;

    public Kursi(String row, String column, String status) {
        this.row = row;
        this.column = column;
        this.status = status;
    }

    public Kursi() {
        this.row = "";
        this.column = "";
        this.status = "0";
    }

    public Kursi(String column) {
        this.row = "";
        this.column = column;
        this.status = "0";
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
