package com.example.elaislami.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SurahFirstResponse {

    @SerializedName("data")
    private ArrayList<SurahsModel> data;

    public ArrayList<SurahsModel> getData() {
        return data;
    }

    public SurahFirstResponse(ArrayList<SurahsModel> data) {
        this.data = data;
    }

}
