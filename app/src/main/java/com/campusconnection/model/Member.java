package com.campusconnection.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Member {

    @SerializedName("id")
    private Integer id;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("subPics")
    private List subPics = new ArrayList();
    @SerializedName("name")
    private String name;
    @SerializedName("city")
    private String city;
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

    public Member(Integer id, String thumbnail, List subPics, String name, String city, String school,
                  String standing, String major, String minor, Integer age, String about, List courses, List interests){
        this.setId(id);
        this.setThumbnail(thumbnail);
        this.setSubPics(subPics);
        this.setName(name);
        this.setCity(city);
        this.setSchool(school);
        this.setStanding(standing);
        this.setMajor(major);
        this.setMinor(minor);
        this.setAge(age);
        this.setAbout(about);
        this.setCourses(courses);
        this.setInterests(interests);
    }


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
