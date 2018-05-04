package com.eyo.bethel.gitnaija.data;

import android.os.Parcel;
import android.os.Parcelable;

import static com.eyo.bethel.gitnaija.Utilities.Utils.computeWeakHash;

public class NaijaDevelopers implements Parcelable {
    private int id;
    private String imageUrl;
    private String profileUrl;
    private String developerUsername;

    public NaijaDevelopers(int id, String imageUrl, String profileUrl, String developerUsername) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.profileUrl = profileUrl;
        this.developerUsername = developerUsername;
    }

    private NaijaDevelopers(Parcel parcel){
        id = parcel.readInt();
        imageUrl = parcel.readString();
        profileUrl = parcel.readString();
        developerUsername = parcel.readString();
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
