package com.campusconnection.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MemberListResponse {

    @SerializedName("error")
    private Boolean error;
    @SerializedName("members")
    private ArrayList<MemberListData> memberList = new ArrayList<>();

    public MemberListResponse(boolean error, ArrayList memberList){
        this.error = error;
        this.memberList = memberList;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public ArrayList<MemberListData> getMemberList() {
        return memberList;
    }

    public void setMemberList(ArrayList memberList) {
        this.memberList = memberList;
    }

    public class MemberListData {

        @SerializedName("memberId")
        private Integer id;
        @SerializedName("thumbnail")
        private String thumbnail;
        @SerializedName("firstname")
        private String firstName;
        @SerializedName("dob")
        private String age;
        @SerializedName("school")
        private String school;
        @SerializedName("standing")
        private String standing;
        @SerializedName("major")
        private String major;
        @SerializedName("minor")
        private String minor;

        public MemberListData(Integer id, String thumbnail, String firstName, String age, String school, String standing, String major, String minor){
            this.id = id;
            this.thumbnail = thumbnail;
            this.firstName = firstName;
            this.age = age;
            this.school = school;
            this.standing = standing;
            this.major = major;
            this.minor = minor;
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

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
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
    }
}
