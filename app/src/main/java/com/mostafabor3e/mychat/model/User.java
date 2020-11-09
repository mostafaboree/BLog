package com.mostafabor3e.mychat.model;

public class User {
    private String id;
    private  String username;
    private String image_url;

    public User(String id, String username, String image_url) {
        this.id = id;
        this.username = username;
        this.image_url = image_url;
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
}
