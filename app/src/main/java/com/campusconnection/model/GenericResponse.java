package com.campusconnection.model;

import com.google.gson.annotations.SerializedName;

public class GenericResponse {

    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;

    public GenericResponse(Boolean error, String message){
        this.setError(error);
        this.setMessage(message);
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
}
