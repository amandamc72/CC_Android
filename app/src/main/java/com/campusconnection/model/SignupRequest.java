package com.campusconnection.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class SignupRequest {

    @SerializedName("code")
    private String code;
    @SerializedName("firstname")
    private String firstName;
    @SerializedName("lastname")
    private String lastName;
    @SerializedName("password")
    private String password;
    @SerializedName("city")
    private String city;
    @SerializedName("state")
    private String state;
    @SerializedName("school")
    private String school;
    @SerializedName("major")
    private String major;
    @SerializedName("minor")
    private String minor;
    @SerializedName("dob")
    private String dob;
    @SerializedName("gender")
    private String gender;

    public SignupRequest(String code, String firstName, String lastName, String password, String city, String state,
                         String school, String major, String minor, String dob, String gender){
        this.code = code;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.city = city;
        this.state = state;
        this.school = school;
        this.major = major;
        this.minor = minor;
        this.dob = dob;
        this.gender = gender;
    }
}
