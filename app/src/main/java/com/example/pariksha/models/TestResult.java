package com.example.pariksha.models;

import java.io.Serializable;

public class TestResult implements Serializable {
    private String TestId;
    private String TestName;

    private int totalNoOfQuestion;
    private int totalMarks;
    private int totalCorrect;
    private int totalIncorrect;
    private int totalPositiveMarks;
    private int totalNegativeMarks;
    private int totalUnattempted;
    private String percentage;
    private String heldOn;
    private String resultStatus;

    public TestResult() {
    }

    public String getTestId() {
        return TestId;
    }

    public void setTestId(String testId) {
        TestId = testId;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public String getHeldOn() {
        return heldOn;
    }

    public void setHeldOn(String heldOn) {
        this.heldOn = heldOn;
    }

    public int getTotalNoOfQuestion() {
        return totalNoOfQuestion;
    }

    public void setTotalNoOfQuestion(int totalNoOfQuestion) {
        this.totalNoOfQuestion = totalNoOfQuestion;
    }

    public int getTotalCorrect() {
        return totalCorrect;
    }

    public void setTotalCorrect(int totalCorrect) {
        this.totalCorrect = totalCorrect;
    }

    public int getTotalIncorrect() {
        return totalIncorrect;
    }

    public void setTotalIncorrect(int totalIncorrect) {
        this.totalIncorrect = totalIncorrect;
    }

    public int getTotalPositiveMarks() {
        return totalPositiveMarks;
    }

    public void setTotalPositiveMarks(int totalPositiveMarks) {
        this.totalPositiveMarks = totalPositiveMarks;
    }

    public int getTotalNegativeMarks() {
        return totalNegativeMarks;
    }

    public void setTotalNegativeMarks(int totalNegativeMarks) {
        this.totalNegativeMarks = totalNegativeMarks;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public int getTotalUnattempted() {
        return totalUnattempted;
    }

    public void setTotalUnattempted(int totalUnattempted) {
        this.totalUnattempted = totalUnattempted;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }
}