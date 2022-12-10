package com.example.elaislami.Model;

import com.google.gson.annotations.SerializedName;

public class AyahModel {

    @SerializedName("text")
    private String text;

    @SerializedName("numberInSurah")
    private int numberInSurah;

    public int getJuz() {
        return juz;
    }

    @SerializedName("juz")
    private int juz;

    public String getAyaContent() {
        return text;
    }

    public int getAyaNo() {
        return numberInSurah;
    }



}
