package com.example.elaislami.Model;

import com.example.elaislami.RoomDBModels.SurahDBModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SurahFirstResponse {

    @SerializedName("data")
    private List<SurahDBModel> data;

    public List<SurahDBModel> getData() {
        return data;
    }

    public SurahFirstResponse(List<SurahDBModel> data) {
        this.data = data;
    }

}
