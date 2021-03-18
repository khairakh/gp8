package com.example.group8;

public class BookingData {

    String PhoneNumber, Name, Time, Date, Services;

    public BookingData() {
    }

    public BookingData(String phoneNumber, String name, String time, String date, String services) {
        PhoneNumber = phoneNumber;
        Name = name;
        Time = time;
        Date = date;
        Services = services;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getServices() {
        return Services;
    }

    public void setServices(String services) {
        Services = services;
    }
}
