package com.example.pariksha.models;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    //  private GoogleSignInAccount googleSignInAccount;
    private String email;
    private String username;
    private String mobileNo;
    private String password;
    private String lastActive;
    private String deviceName;
    private String name;
    private String course;
    private String url_profile_pic;
    private String loginMode;
    private String user_type;
    private int testsAppeared = 0;
    private String lastTestDetails;
    private ArrayList<TestResult> reportCard = new ArrayList<TestResult>();
    private String collegeName = "--";

    public User() {

    }

    public User(String email, String username, String mobileNo, String password, String lastActive, String deviceName, String name, String course, String url_profile_pic, String loginMode, String user_type, int testsAppeared, String lastTestDetails) {
        this.email = email;

        this.username = username;
        this.mobileNo = mobileNo;
        this.password = password;
        this.lastActive = lastActive;
        this.deviceName = deviceName;
        this.name = name;
        this.course = course;
        this.url_profile_pic = url_profile_pic;
        this.loginMode = loginMode;
        this.user_type = user_type;
        this.testsAppeared = testsAppeared;
        this.lastTestDetails = lastTestDetails;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastActive() {
        return lastActive;
    }

    public void setLastActive(String lastActive) {
        this.lastActive = lastActive;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUrl_profile_pic() {
        return url_profile_pic;
    }

    public void setUrl_profile_pic(String url_profile_pic) {
        this.url_profile_pic = url_profile_pic;
    }

    public String getLoginMode() {
        return loginMode;
    }

    public void setLoginMode(String loginMode) {
        this.loginMode = loginMode;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public int getTestsAppeared() {
        return testsAppeared;
    }

    public void setTestsAppeared(int testsAppeared) {
        this.testsAppeared = testsAppeared;
    }

    public String getLastTestDetails() {
        return lastTestDetails;
    }

    public void setLastTestDetails(String lastTestDetails) {
        this.lastTestDetails = lastTestDetails;
    }

    public ArrayList<TestResult> getReportCard() {
        return reportCard;
    }

    public void setReportCard(ArrayList<TestResult> reportCard) {
        this.reportCard = reportCard;
    }
}
