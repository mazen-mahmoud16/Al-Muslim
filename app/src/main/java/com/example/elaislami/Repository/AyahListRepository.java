package com.example.elaislami.Repository;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.elaislami.APIHolders.JsonPlaceHolderAPI;
import com.example.elaislami.DAO.AyahDAO;
import com.example.elaislami.Model.AyahFirstResponse;
import com.example.elaislami.RoomDB.SurahRoomDatabase;
import com.example.elaislami.RoomDBModels.AyahDBModel;
import com.example.elaislami.RoomDBModels.SurahDBModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AyahListRepository {

    private AyahDAO mAyahDao;
    private LiveData<List<AyahDBModel>> mAllAyahs;
    Retrofit retrofit;
    JsonPlaceHolderAPI jsonPlaceHolderAPI;
    Call<AyahFirstResponse> call;
    List<AyahDBModel> mAllAyahsList;
    SharedPreferences settings;
    public static final String PREFS_NAME = "MyPreferenceFile";
    int surahNumber;


    public AyahListRepository(Application application) {

        SurahRoomDatabase db = SurahRoomDatabase.getDatabase(application);

        settings = application.getApplicationContext().getSharedPreferences(PREFS_NAME, 0);



        mAyahDao = db.ayahDAO();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.alquran.cloud/v1/surah/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
        surahNumber = settings.getInt("surahNumber",1);
        call = jsonPlaceHolderAPI.getAyas(surahNumber);
        mAllAyahs = mAyahDao.getAllAyahs();
        mAllAyahs = mAyahDao.getSpecificAyahs(surahNumber);

    }

    public void getAllAyahsApi(){
        call.enqueue(new Callback<AyahFirstResponse>() {
            @Override
            public void onResponse(Call<AyahFirstResponse> call, Response<AyahFirstResponse> response) {
                if (!response.isSuccessful()){
                    Log.d("MVVMX", "--- Not successful");
                } else {

                    AyahFirstResponse mAllAyahsRes = response.body();
                    mAllAyahsList=mAllAyahsRes.getData().getAyahs();

                    for (AyahDBModel ayahDBModel:mAllAyahsList) {
                        ayahDBModel.setSurahNumber(surahNumber);
                        insert(ayahDBModel);
                    }

                }


            }
            @Override
            public void onFailure(Call<AyahFirstResponse> call, Throwable t) {
                Log.d("MVVMX", "--- FAILED " + t.getMessage());
            }
        });
    }


    public LiveData<List<AyahDBModel>> getAllAyahs() {
        return mAllAyahs;
    }

    public void insert (AyahDBModel ayahDBModel) {
        new AyahListRepository.insertAsyncTask(mAyahDao).execute(ayahDBModel);
    }

    private static class insertAsyncTask extends AsyncTask<AyahDBModel, Void, Void> {

        private AyahDAO mAsyncTaskDao;

        insertAsyncTask(AyahDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final AyahDBModel... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
