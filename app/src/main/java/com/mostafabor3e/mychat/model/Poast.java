package com.mostafabor3e.mychat.model;

import com.google.firebase.database.ServerValue;

import java.io.Serializable;

public class Poast implements Serializable {
    private String postKey;
    private String user_id;
    private String title;
    private String descraption;
    private String userPhoto;
    private String postPhoto;
    private Object timeStep;

    public Poast() {
    }

    public Poast( String user_id, String title, String descraption, String userPhoto, String postPhoto) {

        this.user_id = user_id;
        this.title = title;
        this.descraption = descraption;
        this.userPhoto = userPhoto;
        this.postPhoto = postPhoto;
        this.timeStep = ServerValue.TIMESTAMP;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescraption() {
        return descraption;
    }

    public void setDescraption(String descraption) {
        this.descraption = descraption;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getPostPhoto() {
        return postPhoto;
    }

    public void setPostPhoto(String postPhoto) {
        this.postPhoto = postPhoto;
    }

    public Object getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(Object timeStep) {
        this.timeStep = timeStep;
    }
}