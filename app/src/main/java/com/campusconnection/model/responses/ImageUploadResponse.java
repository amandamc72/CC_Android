package com.campusconnection.model.responses;


import com.google.gson.annotations.SerializedName;

public class ImageUploadResponse {

    @SerializedName("error")
    private Boolean error;
    @SerializedName("imgs")
    private String image;

    public ImageUploadResponse(Boolean error, String image) {
        this.error = error;
        this.image = image;
    }

    public Boolean getError() {
        return error;
    }

    public String getImage() {
        return image;
    }

}
