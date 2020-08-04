package com.example.pariksha.models;

import java.io.Serializable;
import java.util.ArrayList;

public class TestResponse implements Serializable {
    private ArrayList<Test> testArrayList;

    public TestResponse() {
    }

    public ArrayList<Test> getTestArrayList() {
        return testArrayList;
    }

    public void setTestArrayList(ArrayList<Test> testArrayList) {
        this.testArrayList = testArrayList;
    }
}
