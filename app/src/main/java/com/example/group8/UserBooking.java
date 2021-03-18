package com.example.group8;

public class UserBooking {
    public String PhoneNumber, Name, Time, Date, Services;

    public UserBooking() {

    }

    public UserBooking (String PhoneNumber, String Name) {
        this.PhoneNumber = PhoneNumber;
        this.Name = Name;

    }

    public UserBooking (String PhoneNumber, String Name, String Time, String Date, String Services) {
        this.PhoneNumber = PhoneNumber;
        this.Name = Name;
        this.Time = Time;
        this.Date = Date;
        this.Services = Services;
    }

    public String getPhoneNumber(){
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber){
        this.PhoneNumber = PhoneNumber;
    }

    public String getName(){
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getTime(){
        return Time;
    }

    public void setTime(String Time){
        this.Time = Time;
    }

    public String getDate(){
        return Date;
    }

    public void setDate(String Date){
        this.Date = Date;
    }

    public String getServices(){
        return Services;
    }

    public void setServices(String Services){
        this.Services = Services;
    }


}
