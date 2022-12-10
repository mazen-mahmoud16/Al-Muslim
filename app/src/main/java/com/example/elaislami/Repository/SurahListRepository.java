package com.example.elaislami.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.elaislami.APIHolders.JsonPlaceHolderAPI;
import com.example.elaislami.DAO.SurahDAO;
import com.example.elaislami.Model.SurahFirstResponse;
import com.example.elaislami.RoomDB.SurahRoomDatabase;
import com.example.elaislami.RoomDBModels.SurahDBModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SurahListRepository {

    private SurahDAO mSurahDao;
    private LiveData<List<SurahDBModel>> mAllSurahs;
    Retrofit retrofit;
    JsonPlaceHolderAPI jsonPlaceHolderAPI;
    Call<SurahFirstResponse> call;
    List<SurahDBModel> mAllSurahsList;

    public SurahListRepository(Application application) {

        SurahRoomDatabase db = SurahRoomDatabase.getDatabase(application);
        mSurahDao = db.surahDAO();
        mAllSurahs = mSurahDao.getAllSurahs();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.alquran.cloud/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
        call = jsonPlaceHolderAPI.getSurahs();

    }


    public void getAllSurahsApi(){
        call.enqueue(new Callback<SurahFirstResponse>() {
            @Override
            public void onResponse(Call<SurahFirstResponse> call, Response<SurahFirstResponse> response) {
                if (!response.isSuccessful()){
                    Log.d("MVVMX", "--- Not successful");
                } else {

                    SurahFirstResponse mAllSurahsRes = response.body();
                    mAllSurahsList=mAllSurahsRes.getData();

                    for (SurahDBModel surahDBModel:mAllSurahsList) {
                        insert(surahDBModel);
                    }
                }
            }
            @Override
            public void onFailure(Call<SurahFirstResponse> call, Throwable t) {
                Log.d("MVVMX", "--- FAILED " + t.getMessage());
            }
        });
    }

    public LiveData<List<SurahDBModel>> getAllSurahs() {
        return mAllSurahs;
    }

    public void insert (SurahDBModel surahModel) {
        new insertAsyncTask(mSurahDao).execute(surahModel);
    }

    private static class insertAsyncTask extends AsyncTask<SurahDBModel, Void, Void> {

        private SurahDAO mAsyncTaskDao;

        insertAsyncTask(SurahDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final SurahDBModel... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}