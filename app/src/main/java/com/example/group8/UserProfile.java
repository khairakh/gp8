package com.example.group8;

public class UserProfile {
    public String userPhoneNumber;
    public String userEmail;
    public String userName;

    public UserProfile() {
    }

    public  UserProfile (String userEmail, String userName, String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
        this.userEmail = userEmail;
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
