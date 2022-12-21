package com.example.elaislami.APIHolder;

import com.example.elaislami.Model.AyahFirstResponse;
import com.example.elaislami.Model.SurahFirstResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/*
 * Here is the Json placeholder api that contains our http requests for APIs
 */
public interface JsonPlaceHolderAPI {

    // To get all surahs
    @GET("surah")
    Call<SurahFirstResponse> getSurahs();

    // To get all ayahs of a specific surah
    @GET("{sura_number}")
    Call<AyahFirstResponse> getAyas(@Path("sura_number")int surah);
}
