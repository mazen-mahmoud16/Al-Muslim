package com.almuslim.elaislami.Model;
import com.google.gson.annotations.SerializedName;

/*
 * This class represents the first response coming from the API, which contains data of AyahResponse type
 */
public class AyahFirstResponse {

    @SerializedName("data")
    private AyahResponse data;

    // To get the ayah response data
    public AyahResponse getData() {
        return data;
    }

}
