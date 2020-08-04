package com.example.pariksha.models;

import android.util.Log;

import com.example.pariksha.util.MYCONSTANTS;
import com.google.gson.Gson;

public class Solution{
    private QuestionPaper questionPaper;
    private String selectedOption = MYCONSTANTS.NOTATTEMPTED;
    private boolean isAttempted = false;

    private static final String TAG = "Solution";

    public Solution() {
    }

    public Solution(QuestionPaper questionPaper, String selectedOption, boolean isAttempted) {
        this.questionPaper = questionPaper;
        this.selectedOption = selectedOption;
        this.isAttempted = isAttempted;
        Log.i(TAG,"Solution : "+new Gson().toJson(questionPaper));
    }

    public QuestionPaper getQuestionPaper() {
        return questionPaper;
    }

    public void setQuestionPaper(QuestionPaper questionPaper) {
        this.questionPaper = questionPaper;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public boolean isAttempted() {
        return isAttempted;
    }

    public void setAttempted(boolean attempted) {
        isAttempted = attempted;
    }
}