package com.example.elaislami.Model;

public class SurahsModel {
    private String english_Name;
    private String arabic_Name;

    public SurahsModel(String englishName, String arabicName) {
        this.english_Name = englishName;
        this.arabic_Name = arabicName;
    }

    public String getEnglish_Name() {
        return english_Name;
    }

    public String getArabic_Name() {
        return arabic_Name;
    }
}


