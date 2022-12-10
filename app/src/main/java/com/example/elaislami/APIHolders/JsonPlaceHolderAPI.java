package com.example.elaislami.APIHolders;

import com.example.elaislami.Model.AyahFirstResponse;
import com.example.elaislami.Model.AyahResponse;
import com.example.elaislami.Model.SurahFirstResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderAPI {
    @GET("surah")
    Call<SurahFirstResponse> getSurahs();

    @GET("{sura_number}")
    Call<AyahFirstResponse> getAyas(@Path("sura_number")int surah);
}
