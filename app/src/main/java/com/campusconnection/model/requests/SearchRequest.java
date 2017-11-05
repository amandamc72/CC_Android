package com.campusconnection.model.requests;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
public class SearchRequest implements Parcelable {

    @SerializedName("offset")
    private int offset;
    @SerializedName("school")
    private String school;
    @SerializedName("major")
    private String major;
    @SerializedName("minor")
    private String minor;
    @SerializedName("ageLow")
    private int ageLow;
    @SerializedName("ageHigh")
    private int ageHigh;
    @SerializedName("gender")
    private String gender;
    @SerializedName("city")
    private String city;
    @SerializedName("state")
    private String state;


    public SearchRequest(int offset, String school, String major, String minor, int ageLow,
                        int ageHigh, String gender, String city, String state){


      this.setOffset(offset);
      this.setSchool(school);
      this.setMajor(major);
      this.setMinor(minor);
      this.setAgeLow(ageLow);
      this.setAgeHigh(ageHigh);
      this.setGender(gender);
      this.setCity(city);
      this.setState(state);

    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
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

    public int getAgeLow() {
        return ageLow;
    }

    public void setAgeLow(int ageLow) {
        this.ageLow = ageLow;
    }

    public int getAgeHigh() {
        return ageHigh;
    }

    public void setAgeHigh(int ageHigh) {
        this.ageHigh = ageHigh;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String toString(){
        return "offset: " + getOffset() + " school: " + getSchool() + " major: " +
                getMajor() + " minor: " + getMinor() + " ageLow: " +
                getAgeLow() + " ageHigh" + getAgeHigh() + " gender: " +
                getGender() + " city: " + getCity() + " state: " + getState();
    }

    //Parcelable
    private SearchRequest(Parcel in){
        setOffset(in.readInt());
        setSchool(in.readString());
        setMajor(in.readString());
        setMinor(in.readString());
        setAgeLow(in.readInt());
        setAgeHigh(in.readInt());
        setGender(in.readString());
        setCity(in.readString());
        setState(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getOffset());
        dest.writeString(getSchool());
        dest.writeString(getMajor());
        dest.writeString(getMinor());
        dest.writeInt(getAgeLow());
        dest.writeInt(getAgeHigh());
        dest.writeString(getGender());
        dest.writeString(getCity());
        dest.writeString(getState());
    }

    public static final Parcelable.Creator<SearchRequest> CREATOR = new Parcelable.Creator<SearchRequest>() {
        @Override
        public SearchRequest createFromParcel(Parcel in) {
            return new SearchRequest(in);
        }

        @Override
        public SearchRequest[] newArray(int size) {
            return new SearchRequest[size];
        }
    };
}
