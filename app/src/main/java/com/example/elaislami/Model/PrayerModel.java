package com.example.elaislami.Model;

import java.util.Calendar;

/*
 * This model is used to represent prayers
 */
public class PrayerModel {

    private Calendar date;
    private String fajr;
    private String dhuhr;
    private String sunrise;
    private String asr;
    private String maghrib;
    private String isha;

    // Here is the constructor
    public PrayerModel(Calendar date, String fajr, String sunrise, String dhuhr, String asr, String maghrib, String isha) {
        this.date = date;
        this.fajr = fajr;
        this.sunrise = sunrise;
        this.dhuhr = dhuhr;
        this.asr = asr;
        this.maghrib = maghrib;
        this.isha = isha;
    }

    /*
     * Setters and getters
     */
    public Calendar getDate() {
        return date;
    }


    public String getFajr() {
        return fajr;
    }

    public String getDhuhr() {
        return dhuhr;
    }


    public String getSunrise() {
        return sunrise;
    }

    public String getAsr() {
        return asr;
    }


    public String getMaghrib() {
        return maghrib;
    }


    public String getIsha() {
        return isha;
    }

}
