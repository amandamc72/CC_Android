package com.campusconnection.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MemberResponse {

    @SerializedName("error")
    private Boolean error;
    @SerializedName("id")
    private Integer id;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("picture")
    private List subPics = new ArrayList();
    @SerializedName("name")
    private String name;
    @SerializedName("city")
    private String city;
    @SerializedName("state")
    private String state;
    @SerializedName("school")
    private String school;
    @SerializedName("standing")
    private String standing;
    @SerializedName("major")
    private String major;
    @SerializedName("minor")
    private String minor;
    @SerializedName("age")
    private Integer age;
    @SerializedName("about")
    private String about;
    @SerializedName("courses")
    private List courses = new ArrayList();
    @SerializedName("interests")
    private List interests = new ArrayList();

    public MemberResponse(Boolean error, Integer id, String thumbnail, List subPics, String name, String city, String school,
                          String standing, String major, String minor, Integer age, String about, List courses, List interests){
        this.error = error;
        this.id = id;
        this.thumbnail = thumbnail;
        this.subPics = subPics;
        this.name = name;
        this.city = city;
        this.school = school;
        this.standing = standing;
        this.major = major;
        this.minor = minor;
        this.age = age;
        this.about = about;
        this.courses = courses;
        this.interests = interests;
    }

    public Boolean getError() {return error;}

    public void setError(Boolean error) {this.error = error;}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List getSubPics() {
        return subPics;
    }

    public void setSubPics(List subPics) {
        this.subPics = subPics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStanding() {
        return standing;
    }

    public void setStanding(String standing) {
        this.standing = standing;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List getCourses() {
        return courses;
    }

    public void setCourses(List courses) {
        this.courses = courses;
    }

    public List getInterests() {
        return interests;
    }

    public void setInterests(List interests) {
        this.interests = interests;
    }
}
