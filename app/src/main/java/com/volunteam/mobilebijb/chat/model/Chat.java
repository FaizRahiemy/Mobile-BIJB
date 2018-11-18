package com.volunteam.mobilebijb.chat.model;

public class Chat {
    private String time;
    private String message;
    private String image;
    private String name;

    public Chat(String time, String message, String image, String name) {
        this.time = time;
        this.message = message;
        this.image = image;
        this.name = name;
    }

    public Chat() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
