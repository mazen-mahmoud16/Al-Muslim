package com.example.elaislami.Model;
import com.example.elaislami.RoomDBModels.SurahDBModel;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/*
 * This model is used to represent first response that come from API
 */
public class SurahFirstResponse {

    @SerializedName("data")
    private final List<SurahDBModel> data;

    /*
     * Setters and getters
     */
    public List<SurahDBModel> getData() {
        return data;
    }

    // Here is the constructor
    public SurahFirstResponse(List<SurahDBModel> data) {
        this.data = data;
    }

}
