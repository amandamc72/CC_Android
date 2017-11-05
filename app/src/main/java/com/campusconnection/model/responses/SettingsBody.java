package com.campusconnection.model.responses;


import com.google.gson.annotations.SerializedName;

public class SettingsBody {

    @SerializedName("error")
    private Boolean error;
    @SerializedName("school")
    private String school;
    @SerializedName("male")
    private int male;
    @SerializedName("female")
    private int female;
    @SerializedName("ageLow")
    private int ageLow;
    @SerializedName("ageHigh")
    private int ageHigh;
    @SerializedName("messages")
    private int messages;
    @SerializedName("matches")
    private int matches;
    @SerializedName("likes")
    private int likes;
    @SerializedName("events")
    private int events;


    public SettingsBody(boolean error, String school, int male, int female, int ageLow,
                        int ageHigh, int messages, int matches, int likes, int events) {

        this.setError(error);
        this.setSchool(school);
        this.setMale(male);
        this.setFemale(female);
        this.setAgeLow(ageLow);
        this.setAgeHigh(ageHigh);
        this.setMessages(messages);
        this.setLikes(likes);
        this.setMatches(matches);
        this.setEvents(events);
    }

    public SettingsBody(String school, int male, int female, int ageLow, int ageHigh,
                        int messages, int matches, int likes ,int events) {
        this.setSchool(school);
        this.setMale(male);
        this.setFemale(female);
        this.setAgeLow(ageLow);
        this.setAgeHigh(ageHigh);
        this.setMessages(messages);
        this.setMatches(matches);
        this.setLikes(likes);
        this.setEvents(events);

    }


    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getMale() {
        return male;
    }

    public void setMale(int male) {
        this.male = male;
    }

    public int getFemale() {
        return female;
    }

    public void setFemale(int female) {
        this.female = female;
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

    public int getMessages() {
        return messages;
    }

    public void setMessages(int messages) {
        this.messages = messages;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getEvents() {
        return events;
    }

    public void setEvents(int events) {
        this.events = events;
    }
}
