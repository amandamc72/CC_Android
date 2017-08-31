package com.campusconnection.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MemberListResponse implements Parcelable {

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


    public static class MemberListData implements Parcelable { //TODO remove static

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

        private MemberListData(Parcel in) {
            id = in.readByte() == 0x00 ? null : in.readInt();
            thumbnail = in.readString();
            firstName = in.readString();
            age = in.readString();
            school = in.readString();
            standing = in.readString();
            major = in.readString();
            minor = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (id == null) {
                dest.writeByte((byte) (0x00));
            } else {
                dest.writeByte((byte) (0x01));
                dest.writeInt(id);
            }
            dest.writeString(thumbnail);
            dest.writeString(firstName);
            dest.writeString(age);
            dest.writeString(school);
            dest.writeString(standing);
            dest.writeString(major);
            dest.writeString(minor);
        }

        @SuppressWarnings("unused")
        public final Parcelable.Creator<MemberListData> CREATOR = new Parcelable.Creator<MemberListData>() {
            @Override
            public MemberListData createFromParcel(Parcel in) {
                return new MemberListData(in);
            }

            @Override
            public MemberListData[] newArray(int size) {
                return new MemberListData[size];
            }
        };
    }


    private MemberListResponse(Parcel in) {
        byte errorVal = in.readByte();
        error = errorVal == 0x02 ? null : errorVal != 0x00;
        if (in.readByte() == 0x01) {
            memberList = new ArrayList<MemberListData>();
            in.readList(memberList, MemberListData.class.getClassLoader());
        } else {
            memberList = null;
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
        if (memberList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(memberList);
        }
    }

    public final Parcelable.Creator<MemberListResponse> CREATOR = new Parcelable.Creator<MemberListResponse>() {
        @Override
        public MemberListResponse createFromParcel(Parcel in) {
            return new MemberListResponse(in);
        }

        @Override
        public MemberListResponse[] newArray(int size) {
            return new MemberListResponse[size];
        }
    };
}