package com.example.rasachatbotapp;

public class userHelper {

    String fname, userage ,telNum, gurtelNum, password, cpassword;

    public userHelper(){
    }

    public userHelper(String fname, String userage, String telNum, String gurtelNum, String password, String cpassword) {
        this.fname = fname;
        this.userage = userage;
        this.telNum = telNum;
        this.gurtelNum = gurtelNum;
        this.password = password;
        this.cpassword = cpassword;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getUserage() {
        return userage;
    }

    public void setUserage(String userage) {
        this.userage = userage;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String getGurtelNum() {
        return gurtelNum;
    }

    public void setGurtelNum(String telNum) {
        this.gurtelNum = gurtelNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }
}
