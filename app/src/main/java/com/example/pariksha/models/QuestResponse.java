package com.example.pariksha.models;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestResponse implements Serializable {

    private String test_id;
    private String created_by;
    private String timestamp;
    private ArrayList<QuestionPaper> questionPaper;

    public QuestResponse() {
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<QuestionPaper> getQuestionPaper() {
        return questionPaper;
    }

    public void setQuestionPaper(ArrayList<QuestionPaper> questionPaper) {
        this.questionPaper = questionPaper;
    }
}
