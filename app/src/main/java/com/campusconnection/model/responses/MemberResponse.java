package com.campusconnection.model.responses;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemberResponse implements Parcelable {

    @SerializedName("error")
    private Boolean error;
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
    //private String url = "http://campusconnection.ddns.net:8080/";


    public MemberResponse(Boolean error, String thumbnail, List subPics, String name, String city, String school,
                          String standing, String major, String minor, Integer age, String about, List courses, List interests){
        this.error = error;
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

    //Constrctor to update profile data
    public MemberResponse(String school, String major, String minor, String city, String state, String standing, String about, List interests) {
        this.city = city;
        this.state = state;
        this.school = school;
        this.standing = standing;
        this.major = major;
        this.minor = minor;
        this.about = about;
        this.interests = interests;
    }

    public Boolean getError() {return error;}

    public void setError(Boolean error) {this.error = error;}

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

    public ArrayList<String> formatAllPicsToArray() {
        ArrayList<String> formatedPics = new ArrayList<>();
        for (int i = 0; i < getSubPics().size(); i++) {
            try {
                LinkedTreeMap<String,String> currentPicMap = (LinkedTreeMap<String,String>)getSubPics().get(i);
                formatedPics.add(i , currentPicMap.get("relPath"));
            } catch (ClassCastException e) {
                HashMap<String,String> currentPicMap = (HashMap<String,String>)getSubPics().get(i);
                formatedPics.add(i , currentPicMap.get("relPath"));
            }
        }
        formatedPics.add(0, getThumbnail());
        return formatedPics;
    }

    private MemberResponse(Parcel in) {
        byte errorVal = in.readByte();
        error = errorVal == 0x02 ? null : errorVal != 0x00;
        thumbnail = in.readString();
        name = in.readString();
        city = in.readString();
        state = in.readString();
        school = in.readString();
        standing = in.readString();
        major = in.readString();
        minor = in.readString();
        age = in.readByte() == 0x00 ? null : in.readInt();
        about = in.readString();
        if (in.readByte() == 0x01) {
            subPics = new ArrayList<>();
            in.readList(subPics, getClass().getClassLoader());
        } else {
            subPics = null;
        }
        if (in.readByte() == 0x01) {
            courses = new ArrayList<>();
            in.readList(courses, getClass().getClassLoader());
        } else {
            courses = null;
        }
        if (in.readByte() == 0x01) {
            interests = new ArrayList<>();
            in.readList(interests, getClass().getClassLoader());

        } else {
            interests = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (error == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (error ? 0x01 : 0x00));
        }
        dest.writeString(thumbnail);
        dest.writeString(name);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(school);
        dest.writeString(standing);
        dest.writeString(major);
        dest.writeString(minor);
        if (age == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(age);
        }
        dest.writeString(about);
        if (subPics == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(subPics);
        }
        if (courses == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(courses);
        }
        if (interests == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(interests);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MemberResponse> CREATOR = new Parcelable.Creator<MemberResponse>() {
        @Override
        public MemberResponse createFromParcel(Parcel in) {
            return new MemberResponse(in);
        }

        @Override
        public MemberResponse[] newArray(int size) {
            return new MemberResponse[size];
        }
    };
}
