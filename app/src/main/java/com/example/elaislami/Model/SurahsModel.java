package com.example.elaislami.Model;

import com.google.gson.annotations.SerializedName;

public class SurahsModel {

    @SerializedName("number")
    private int number;

    @SerializedName("name")
    private String name;

    @SerializedName("englishName")
    private String englishName;

    public SurahsModel(int number, String name, String englishName) {
        this.number = number;
        this.name = name;
        this.englishName = englishName;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getEnglishName() {
        return englishName;
    }
}


