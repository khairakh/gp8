package com.example.group8;

public class UserProfile {
    public String PhoneNumber;
    public String Email;
    public String Name;

    public  UserProfile (String userPhoneNumber, String userEmail , String userName ) {
        this.PhoneNumber = userPhoneNumber;
        this.Email = userEmail;
        this.Name = userName;
    }
}
