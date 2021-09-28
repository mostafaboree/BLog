package com.mostafabor3e.mychat.model;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class Profile extends User {
    private String bio;
    private String phone;

    public Profile() {
    }

    public Profile(String id, String username, String image_url, String gmail, String bio, String phone) {
        super(id, username, image_url, gmail);
        this.bio = bio;
        this.phone = phone;
    }

    public Profile(String bio, String phone) {
        this.bio = bio;
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public void setGmail(String gmail) {
        super.setGmail(gmail);
    }
    @BindingAdapter("LoadImage")
    public static void loadImage(View view,String ImageUrl){
        ImageView imageView= (ImageView) view;
        Glide.with(imageView.getContext()).asBitmap().load(ImageUrl).into(imageView);
    }
}
