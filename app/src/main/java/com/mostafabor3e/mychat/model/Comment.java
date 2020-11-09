package com.mostafabor3e.mychat.model;

import com.google.firebase.database.ServerValue;

public class Comment {
    private String uid,uName,content,uPhoto;
    private Object timeStemp;

    public Comment() {
    }

    public Comment(String uid, String uName, String content, String uPhoto) {
        this.uid = uid;
        this.uName = uName;
        this.content = content;
        this.uPhoto = uPhoto;
        this.timeStemp = ServerValue.TIMESTAMP;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getuPhoto() {
        return uPhoto;
    }

    public void setuPhoto(String uPhoto) {
        this.uPhoto = uPhoto;
    }

    public Object getTimeStemp() {
        return timeStemp;
    }

    public void setTimeStemp(Object timeStemp) {
        this.timeStemp = timeStemp;
    }
}
