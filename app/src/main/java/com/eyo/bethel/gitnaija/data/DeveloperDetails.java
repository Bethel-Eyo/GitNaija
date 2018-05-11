package com.eyo.bethel.gitnaija.data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeveloperDetails {
    @SerializedName("name")
    @Expose
    private String fullName;
    @SerializedName("bio")
    @Expose
    private String devBiography;
    @SerializedName("company")
    @Expose
    private String devWorkPlace;
    @SerializedName("public_repos")
    @Expose
    private int publicRepos;

    public DeveloperDetails(String fullName, String devBiography, String devWorkPlace, int publicRepos) {
        this.fullName = fullName;
        this.devBiography = devBiography;
        this.devWorkPlace = devWorkPlace;
        this.publicRepos = publicRepos;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDevBiography() {
        return devBiography;
    }

    public void setDevBiography(String devBiography) {
        this.devBiography = devBiography;
    }

    public String getDevWorkPlace() {
        return devWorkPlace;
    }

    public void setDevWorkPlace(String devWorkPlace) {
        this.devWorkPlace = devWorkPlace;
    }

    public int getPublicRepos() {
        return publicRepos;
    }

    public void setPublicRepos(int publicRepos) {
        this.publicRepos = publicRepos;
    }
}

