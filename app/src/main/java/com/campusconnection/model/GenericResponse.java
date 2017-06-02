package com.campusconnection.model;

import com.google.gson.annotations.SerializedName;

public class GenericResponse {

    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private String code;

    public GenericResponse(Boolean error, String message, String code){
        this.error = error;
        this.message = message;
        this.code = code;
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

}
