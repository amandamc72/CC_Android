package com.campusconnection.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

// Also covers cases for register response and add picture response
public class GenericResponse {

    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private String code;
    @SerializedName("imgs")
    private List img = new ArrayList();


    public GenericResponse(Boolean error, String message, String code, List img){
        this.error = error;
        this.message = message;
        this.code = code;
        this.img = img;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List getImg() { return img; }

    public void setImg(List img) { this.img = img; }

}
