package com.volunteam.mobilebijb.parking;

import java.util.ArrayList;

public class HisrotyResponse {
    private float status_code;
    private String status;
    ArrayList < Result_parking > result_parking = new ArrayList< Result_parking >();


    // Getter Methods

    public float getStatus_code() {
        return status_code;
    }

    public String getStatus() {
        return status;
    }

    // Setter Methods

    public void setStatus_code(float status_code) {
        this.status_code = status_code;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Result_parking> getResult_parking() {
        return result_parking;
    }

    public void setResult_parking(ArrayList<Result_parking> result_parking) {
        this.result_parking = result_parking;
    }
}
