package com.campusconnection.model.requests;

import com.google.gson.annotations.SerializedName;


public class RemoveRequest {

    @SerializedName("src")
    private String src;
    @SerializedName("flag")
    private String flag;

    public RemoveRequest(String src, String flag) {
        this.src = src;
        this.flag = flag;
    }
}
