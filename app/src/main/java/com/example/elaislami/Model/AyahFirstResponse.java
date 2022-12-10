package com.example.elaislami.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AyahFirstResponse {
    @SerializedName("data")
    private AyahResponse data;

    public AyahResponse getData() {
        return data;
    }


}
