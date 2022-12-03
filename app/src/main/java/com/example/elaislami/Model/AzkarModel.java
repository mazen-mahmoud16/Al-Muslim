package com.example.elaislami.Model;

public class AzkarModel {

    private String zekrContent;
    private int noOfTimes;

    public AzkarModel(String zekrContent, int no_of_times) {
        this.zekrContent = zekrContent;
        this.noOfTimes = no_of_times;
    }

    public String getZekrContent() {
        return zekrContent;
    }

    public int getNoOfTimes() {
        return noOfTimes;
    }
}
