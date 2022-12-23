package com.example.patrollingapp.PatrolOfficer;

public class RowdyAddingClass {

    String name, hs, Cases;
    double Lati, Longi;

    public RowdyAddingClass(String name, String hs, double lati, double longi, String cases) {
        this.name = name;
        this.hs = hs;
        this.Lati = lati;
        this.Longi = longi;
        this.Cases = cases;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHs() {
        return hs;
    }

    public void setHs(String hs) {
        this.hs = hs;
    }

    public double getLati() {
        return Lati;
    }

    public void setLati(double lati) {
        Lati = lati;
    }

    public double getLongi() {
        return Longi;
    }

    public void setLongi(double longi) {
        Longi = longi;
    }

    public String getCases() {
        return Cases;
    }

    public void setCases(String cases) {
        Cases = cases;
    }
}