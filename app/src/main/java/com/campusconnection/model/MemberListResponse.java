package com.campusconnection.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MemberListResponse {

    @SerializedName("member_list")
    private List memberList = new ArrayList();

    public MemberListResponse(List memberList){
        this.memberList = memberList;
    }

    public List getMemberList() {
        return memberList;
    }

    public void setMemberList(List memberList) {
        this.memberList = memberList;
    }

    public static class MemberListData {

        @SerializedName("id")
        private Integer id;
        @SerializedName("thumbnail")
        private String thumbnail;
        @SerializedName("first_name")
        private String firstName;
        @SerializedName("age")
        private Integer age;
        @SerializedName("school")
        private String school;
        @SerializedName("standing")
        private String standing;
        @SerializedName("major")
        private String major;
        @SerializedName("minor")
        private String minor;

        public MemberListData(Integer id, String thumbnail, String firstName, Integer age, String school, String standing, String major, String minor){
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

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
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
