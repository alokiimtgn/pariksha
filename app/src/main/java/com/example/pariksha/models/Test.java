package com.example.pariksha.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Test implements Serializable {
    @SerializedName("testID")
    private String testID;
    @SerializedName("heading")
    private String heading;
    @SerializedName("createdBy")
    private String createdBy;
    @SerializedName("timestamp")
    private String timestamp;
    @SerializedName("isTestActive")
    private boolean isTestActive;
    @SerializedName("testDescription")
    private String testDescription;
    @SerializedName("url")
    private String url;
    @SerializedName("audience")
    private String audience;

    public Test() {
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isTestActive() {
        return isTestActive;
    }

    public void setTestActive(boolean testActive) {
        isTestActive = testActive;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }


}