package com.campusconnection.model;


import com.google.gson.annotations.SerializedName;

public class RegisterRequest {

    @SerializedName("email")
    private String email;


    public RegisterRequest(String email) {
        this.email = email;
    }
}
