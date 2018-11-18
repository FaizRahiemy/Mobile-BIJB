package com.volunteam.mobilebijb.login;

public class Result_login {
    private String id;
    private String nama;
    private String email;
    private String password;
    private String pin;
    private String photo;
    private String saldo;
    private String unverified;
    private String verifykey;
    private String token;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPin() {
        return pin;
    }

    public String getPhoto() {
        return photo;
    }

    public String getSaldo() {
        return saldo;
    }

    public String getUnverified() {
        return unverified;
    }

    public String getVerifykey() {
        return verifykey;
    }

    public String getToken() {
        return token;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public void setUnverified(String unverified) {
        this.unverified = unverified;
    }

    public void setVerifykey(String verifykey) {
        this.verifykey = verifykey;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
