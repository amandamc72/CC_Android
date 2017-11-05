package com.campusconnection.model.requests;

import com.google.gson.annotations.SerializedName;


public class RemoveRequest {

    @SerializedName("name")
    private String name;
    @SerializedName("flag")
    private String flag;

    public RemoveRequest(String name, String flag) {
        this.name = name;
        this.flag = flag;
    }
}
