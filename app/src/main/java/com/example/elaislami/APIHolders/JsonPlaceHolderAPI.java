package com.example.elaislami.APIHolders;

import com.example.elaislami.Model.SurahFirstResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderAPI {
    @GET("surah")
    Call<SurahFirstResponse> getSurahs();
}
