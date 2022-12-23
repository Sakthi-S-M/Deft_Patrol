package com.example.patrollingapp;

public class UserHelperClass {
    String name,username,phoneNo,password,role;

    public UserHelperClass(String user_days) {

    }

    public UserHelperClass(String name, String username, String phoneNo, String password, String role) {
        this.name = name;
        this.username = username;
        this.phoneNo = phoneNo;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
