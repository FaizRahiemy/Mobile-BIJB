package com.volunteam.mobilebijb.profile;

public class User {

    private String id;
    private String nama;
    private String email;
    private String password;
    private String pin;
    private String saldo;
    private String photo;
    private String token;

    public User(String nama, String email, String password) {
        this.nama = nama;
        this.email = email;
        this.password = password;
    }

    public User(String nama, String email, String password, String pin) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.pin = pin;
    }

    public User(String nama, String email, String password, String pin, String saldo) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.pin = pin;
        this.saldo = saldo;
    }

    public User(String id, String nama, String email, String password, String pin, String saldo, String photo, String token) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.pin = pin;
        this.saldo = saldo;
        this.photo = photo;
        this.token = token;
    }

    public User() {
        this.email = "";
        this.nama = "";
        this.password = "";
        this.pin = "";
        this.saldo = "";
        this.photo = "";
        this.token = "";
    }

    public User(String id, String token) {
        this.id = id;
        this.token = token;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
