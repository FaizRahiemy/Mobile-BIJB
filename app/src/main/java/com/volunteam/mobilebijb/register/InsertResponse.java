package com.volunteam.mobilebijb.register;

public class InsertResponse {
    String status_code;
    String status;

    public InsertResponse(String status_code, String status) {
        this.status_code = status_code;
        this.status = status;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
