package com.mostafabor3e.mychat.model;

public class User {
    protected String id;
    protected   String username;
    protected String image_url;
    protected  String gmail;


    public User(String id, String username, String image_url) {
        this.id = id;
        this.username = username;
        this.image_url = image_url;
    }

    public User(String id, String username, String image_url, String gmail) {
        this.id = id;
        this.username = username;
        this.image_url = image_url;
        this.gmail = gmail;
    }

    public User(){

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
}
