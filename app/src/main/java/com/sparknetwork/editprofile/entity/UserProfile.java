package com.sparknetwork.editprofile.entity;

import java.io.Serializable;

public class UserProfile implements Serializable {

    private String nick;
    private String photoUrl;
    private String displayName;
    private String realName;
    private String gender;
    private String ethnicity;
    private String religion;
    private String figure;
    private String maritalStatus;
    private String occupation;
    private String aboutMe;
    private String location;

    public UserProfile() {
    }

    public UserProfile(String nick, String url, String displayName, String realName, String gender, String ethnicity, String religion, String figure, String marital_status, String occupation, String aboutMe, String location) {
        this.nick = nick;
        this.photoUrl = url;
        this.displayName = displayName;
        this.realName = realName;
        this.gender = gender;
        this.ethnicity = ethnicity;
        this.religion = religion;
        this.figure = figure;
        this.maritalStatus = marital_status;
        this.occupation = occupation;
        this.aboutMe = aboutMe;
        this.location = location;
    }

    public String getNick() {
        return nick;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
