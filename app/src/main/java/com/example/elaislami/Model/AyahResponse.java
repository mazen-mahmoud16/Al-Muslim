package com.example.elaislami.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AyahResponse {

    @SerializedName("ayahs")
    private List<AyahModel> ayahs;

    public List<AyahModel> getAyahs() {
        return ayahs;
    }


}
