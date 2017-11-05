package com.campusconnection.model.requests;


import com.google.gson.annotations.SerializedName;

public class SwipeRequest {

    @SerializedName("target")
    private int target;
    @SerializedName("swipeType")
    private int swipeType;

    public SwipeRequest(int target, int type) {
        this.target = target;
        swipeType = type;
    }
}
