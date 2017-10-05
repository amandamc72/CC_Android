package com.campusconnection.model;

import com.google.gson.annotations.SerializedName;


public class DeletePictureRequest {

    @SerializedName("src")
    private String src;
    @SerializedName("flag")
    private String flag;

    public DeletePictureRequest(String src, String flag) {
        this.src = src;
        this.flag = flag;
    }
}
