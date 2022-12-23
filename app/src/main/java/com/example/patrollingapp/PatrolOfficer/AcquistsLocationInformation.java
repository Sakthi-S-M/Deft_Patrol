package com.example.patrollingapp.PatrolOfficer;

public class AcquistsLocationInformation {

    public double lati;
    public double longi;
    public String name;
    public String hs;

    public AcquistsLocationInformation(){               //default constructor which invokes on object creation of respective class in MainActivity.java

    }

    public AcquistsLocationInformation(double lati, double longi, String name, String hs){    //parameterized constructor which will store the retrieved data from firebase
        this.lati = lati;
        this.longi = longi;
        this.name=name;
        this.hs=hs;
    }
}
