package com.example.elaislami.Model;

public class SurahDetailModel {
    private String ayaContent;
    private int ayaNumber;

    public String getAyaContent() {
        return ayaContent;
    }

    public int getAyaNumber() {
        return ayaNumber;
    }

    public SurahDetailModel(String ayaContent, int ayaNumber) {
        this.ayaContent = ayaContent;
        this.ayaNumber = ayaNumber;
    }
}
