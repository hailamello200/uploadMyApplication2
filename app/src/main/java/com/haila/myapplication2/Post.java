package com.haila.myapplication2;

import com.google.firebase.Timestamp;

public class Post {
    String post_text, userID;
    Timestamp post_date;

    public Post() {

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Post(String post_text, String userID, Timestamp post_date) {
        this.post_text = post_text;
        this.post_date = post_date;
        this.userID = userID;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    public Timestamp getPost_date() {
        return post_date;
    }

    public void setPost_date(Timestamp post_date) {
        this.post_date = post_date;
    }
}
