package com.example.elaislami.Model;

public class PrayerModel {

    private String salatName;
    private String salatTime;

    public String getSalatName() {
        return salatName;
    }

    public String getSalatTime() {
        return salatTime;
    }

    public PrayerModel(String salatName, String salatTime) {
        this.salatName = salatName;
        this.salatTime = salatTime;
    }
}
