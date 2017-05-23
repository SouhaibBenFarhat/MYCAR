package com.esprit.nolife.carsstore.entities;

/**
 * created by Souhaib on 05/11/2016.
 */

public class User {

    private String userUID;
    private String password;
    private String signedIn;
    private String provider;
    private String email;
    private String profilePictureUri;
    private String name;
    private String connectedWith;

    public User() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConnectedWith() {
        return connectedWith;
    }

    public void setConnectedWith(String connectedWith) {
        this.connectedWith = connectedWith;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getSignedIn() {
        return signedIn;
    }

    public void setSignedIn(String signedIn) {
        this.signedIn = signedIn;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePictureUri() {
        return profilePictureUri;
    }

    public void setProfilePictureUri(String profilePictureUri) {
        this.profilePictureUri = profilePictureUri;
    }
}
