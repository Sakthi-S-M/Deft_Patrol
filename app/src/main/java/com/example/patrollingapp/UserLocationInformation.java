package com.example.patrollingapp;

public class UserLocationInformation {

    public String date;
    public double latitude;
    public double longitude;
    public String name;

    public UserLocationInformation(){               //default constructor which invokes on object creation of respective class in MainActivity.java

    }

    public UserLocationInformation(String date, double latitude, double longitude, String name){    //parameterized constructor which will store the retrieved data from firebase
        this.date=date;
        this.latitude=latitude;
        this.longitude=longitude;
        this.name=name;
    }
}
