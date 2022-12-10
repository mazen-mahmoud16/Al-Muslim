package com.example.elaislami.Model;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

public class SurahModel {

    @SerializedName("number")
    private int number;

    @SerializedName("name")
    private String name;

    @SerializedName("englishName")
    private String englishName;

    public SurahModel(int number, String name, String englishName) {
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


