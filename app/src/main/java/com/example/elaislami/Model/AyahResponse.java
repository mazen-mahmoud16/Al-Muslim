package com.example.elaislami.Model;

import com.example.elaislami.RoomDBModels.AyahDBModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AyahResponse {

    @SerializedName("ayahs")
    private List<AyahDBModel> ayahs;


    public List<AyahDBModel> getAyahs() {
        return ayahs;
    }


}
