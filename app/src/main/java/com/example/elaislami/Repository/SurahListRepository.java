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

/*
 * Here is the repository for surah list
 */
public class SurahListRepository {

    // Make a reference for the dao
    private final SurahDAO mSurahDao;

    // Make a live data list to keep updates of the current list contents
    private final LiveData<List<SurahDBModel>> mAllSurahs;

    // Make a call of the type SurahFirstResponse that will map returned data from API after calling api method
    private final Call<SurahFirstResponse> call;

    // Make a list to to store the data from API
    private List<SurahDBModel> mAllSurahsList;

    // Here is the constructor
    public SurahListRepository(Application application) {

        SurahRoomDatabase db = SurahRoomDatabase.getDatabase(application);
        mSurahDao = db.surahDAO();
        mAllSurahs = mSurahDao.getAllSurahs();

        // Make a retrofit to call builder to get data from API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.alquran.cloud/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Make a JSON placeholder API reference to JsonPlaceHolderAPI interface to access API methods
        JsonPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
        call = jsonPlaceHolderAPI.getSurahs();
    }

    /*
     * Here is the function to get the data from API
     */
    public void getAllSurahsApi(){
        // Asynchronously send the request and notify callback of its response or if an error occurred talking to the server, creating the request, or processing the response
        call.enqueue(new Callback<SurahFirstResponse>() {
            @Override
            public void onResponse(Call<SurahFirstResponse> call, Response<SurahFirstResponse> response) {
                if (!response.isSuccessful()){
                    Log.d("MVVMX", "--- Not successful");
                } else {

                    SurahFirstResponse mAllSurahsRes = response.body();
                    assert mAllSurahsRes != null;
                    mAllSurahsList=mAllSurahsRes.getData();

                    // Looping over the list got from the API response body
                    for (SurahDBModel surahDBModel:mAllSurahsList) {
                        // Inserting in the ROOM DB
                        insert(surahDBModel);
                    }
                }
            }

            // OnFailure which get the data from ROOM DB in case of lost internet connection
            @Override
            public void onFailure(Call<SurahFirstResponse> call, Throwable t) {
                Log.d("MVVMX", "--- FAILED " + t.getMessage());
            }
        });
    }

    // Here is the function to get all surahs from ROOM DB
    public LiveData<List<SurahDBModel>> getAllSurahs() {
        return mAllSurahs;
    }

    // Here is the function to insert surah in ROOM DB
    public void insert (SurahDBModel surahModel) {
        new insertAsyncTask(mSurahDao).execute(surahModel);
    }

    // Here is the function to get insert surah Asynchronously in ROOM DB
    private static class insertAsyncTask extends AsyncTask<SurahDBModel, Void, Void> {

        private final SurahDAO mAsyncTaskDao;

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