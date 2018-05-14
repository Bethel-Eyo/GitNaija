package com.eyo.bethel.gitnaija.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.eyo.bethel.gitnaija.Utilities.Utils.computeWeakHash;

public class NaijaDevelopers implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("avatar_url")
    @Expose
    private String imageUrl;
    @SerializedName("html_url")
    @Expose
    private String profileUrl;
    @SerializedName("login")
    @Expose
    private String developerUsername;
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

    public NaijaDevelopers(int id, String imageUrl, String profileUrl, String developerUsername, String fullName, String devBiography, String devWorkPlace, int publicRepos) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.profileUrl = profileUrl;
        this.developerUsername = developerUsername;
        this.fullName = fullName;
        this.devBiography = devBiography;
        this.devWorkPlace = devWorkPlace;
        this.publicRepos = publicRepos;
    }

    public NaijaDevelopers() {
    }

    private NaijaDevelopers(Parcel parcel){
        id = parcel.readInt();
        imageUrl = parcel.readString();
        profileUrl = parcel.readString();
        developerUsername = parcel.readString();
        fullName = parcel.readString();
        devBiography = parcel.readString();
        devWorkPlace = parcel.readString();
        publicRepos = parcel.readInt();
    }

    public String getImportedHashCode(){
        StringBuilder builder = new StringBuilder();

        builder.append("id").append(id == 0 ? "" : id)
                .append("login").append(developerUsername == null ? "" : developerUsername)
                .append("avatar_url").append(imageUrl == null ? "" : imageUrl)
                .append("html_url").append(profileUrl == null ? "" : profileUrl);

        return computeWeakHash(builder.toString());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getDeveloperUsername() {
        return developerUsername;
    }

    public void setDeveloperUsername(String developerUsername) {
        this.developerUsername = developerUsername;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(imageUrl);
        dest.writeString(profileUrl);
        dest.writeString(developerUsername);
        dest.writeString(fullName);
        dest.writeString(devBiography);
        dest.writeString(devWorkPlace);
        dest.writeInt(publicRepos);
    }

    public static final Parcelable.Creator<NaijaDevelopers> CREATOR = new Parcelable.Creator<NaijaDevelopers>() {
        @Override
        public NaijaDevelopers createFromParcel(Parcel source) {
            return new NaijaDevelopers(source);
        }

        @Override
        public NaijaDevelopers[] newArray(int size) {
            return new NaijaDevelopers[size];
        }
    };
}
