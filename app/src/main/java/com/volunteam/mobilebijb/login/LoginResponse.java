package com.volunteam.mobilebijb.login;

public class LoginResponse {
    private float status_code;
    private String status;
    private Result_login result_login;


    // Getter Methods

    public float getStatus_code() {
        return status_code;
    }

    public String getStatus() {
        return status;
    }

    public Result_login getResult_login() {
        return result_login;
    }

    // Setter Methods

    public void setStatus_code(float status_code) {
        this.status_code = status_code;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResult_login(Result_login result_loginObject) {
        this.result_login = result_loginObject;
    }
}
