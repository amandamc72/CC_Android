package com.campusconnection.model;

import com.google.gson.annotations.SerializedName;

public class MemberSessionResponse {

    @SerializedName("error")
    private Boolean error;
    @SerializedName("id")
    private Integer id;

    public MemberSessionResponse(Boolean error, Integer id){
        this.error = error;
        this.id = id;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
