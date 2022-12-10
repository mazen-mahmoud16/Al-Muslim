package com.example.elaislami.Model;

import com.example.elaislami.RoomDBModels.SurahDBModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SurahFirstResponse {

    @SerializedName("data")
    private List<SurahModel> data;

    public List<SurahModel> getData() {
        return data;
    }

    public SurahFirstResponse(List<SurahModel> data) {
        this.data = data;
    }

}
