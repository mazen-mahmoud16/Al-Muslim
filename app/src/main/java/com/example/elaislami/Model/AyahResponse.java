package com.example.elaislami.Model;

import com.example.elaislami.RoomDBModels.AyahDBModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
 * This class represents the final response coming from the API, which contains list of Ayahs
 */
public class AyahResponse {

    // Here is the variable that is saved in it the list of ayahs
    @SerializedName("ayahs")
    private List<AyahDBModel> ayahs;

    // To get the list of ayas
    public List<AyahDBModel> getAyahs() {
        return ayahs;
    }


}
