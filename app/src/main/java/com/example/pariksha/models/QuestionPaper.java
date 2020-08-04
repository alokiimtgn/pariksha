package com.example.pariksha.models;

import com.example.pariksha.util.MYCONSTANTS;
import com.google.gson.annotations.SerializedName;

public class QuestionPaper {
    @SerializedName("quesNo")
    private int quesNo;
    @SerializedName("question")
    private String question;
    @SerializedName("firstOption")
    private String firstOption;
    @SerializedName("secondOption")
    private String secondOption;
    @SerializedName("thirdOption")
    private String thirdOption;
    @SerializedName("fourthOption")
    private String fourthOption;
    @SerializedName("correctOption")
    private String correctOption;
    @SerializedName("marks")
    private int marks;// = MYCONSTANTS.MARKS_CORRECT_ATTEMPT;
    @SerializedName("negmark")
    private int negmark;// = MYCONSTANTS.MARKS_INCORRECT_ATTEMPT;
    @SerializedName("isNegMarkActive")
    private boolean isNegMarkActive;// = MYCONSTANTS.ISNEGMARKACTIVE;


    public QuestionPaper() {
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }

    public int getQuesNo() {
        return quesNo;
    }

    public void setQuesNo(int quesNo) {
        this.quesNo = quesNo;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFirstOption() {
        return firstOption;
    }

    public void setFirstOption(String firstOption) {
        this.firstOption = firstOption;
    }

    public String getSecondOption() {
        return secondOption;
    }

    public void setSecondOption(String secondOption) {
        this.secondOption = secondOption;
    }

    public String getThirdOption() {
        return thirdOption;
    }

    public void setThirdOption(String thirdOption) {
        this.thirdOption = thirdOption;
    }

    public String getFourthOption() {
        return fourthOption;
    }

    public void setFourthOption(String fourthOption) {
        this.fourthOption = fourthOption;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public int getNegmark() {
        return negmark;
    }

    public void setNegmark(int negmark) {
        this.negmark = negmark;
    }

    public boolean isNegMarkActive() {
        return isNegMarkActive;
    }

    public void setNegMarkActive(boolean negMarkActive) {
        isNegMarkActive = negMarkActive;
    }
}
