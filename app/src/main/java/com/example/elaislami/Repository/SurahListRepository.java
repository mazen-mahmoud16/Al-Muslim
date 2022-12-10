package com.example.elaislami.Repository;

import android.app.Application;
import android.util.Log;

import com.example.elaislami.APIHolders.JsonPlaceHolderAPI;
import com.example.elaislami.Model.SurahFirstResponse;
import com.example.elaislami.Model.SurahsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SurahListRepository {

    Retrofit retrofit;
    JsonPlaceHolderAPI jsonPlaceHolderAPI;
    Call<SurahFirstResponse> call;
    ArrayList<SurahsModel> mAllSurahsList;

    public SurahListRepository(Application application) {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.alquran.cloud/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
        call = jsonPlaceHolderAPI.getSurahs();
    }


    public ArrayList<SurahsModel> getAllSurahs(){
        call.enqueue(new Callback<SurahFirstResponse>() {
            @Override
            public void onResponse(Call<SurahFirstResponse> call, Response<SurahFirstResponse> response) {
                if (!response.isSuccessful()){
                    Log.d("MVVMX", "--- Not successful");
                } else {
                    SurahFirstResponse mAllSurahs = response.body();
                    mAllSurahsList=mAllSurahs.getData();
                    Log.d("aaa",mAllSurahsList.get(0).getName());
                }
            }
            @Override
            public void onFailure(Call<SurahFirstResponse> call, Throwable t) {
                Log.d("MVVMX", "--- FAILED " + t.getMessage());
            }
        });
        return mAllSurahsList;
    }
}