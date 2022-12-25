package com.almuslim.elaislami.RoomDBManager.Repository;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.almuslim.elaislami.APIHolder.JsonPlaceHolderAPI;
import com.almuslim.elaislami.Model.AyahFirstResponse;
import com.almuslim.elaislami.RoomDBManager.RoomDB.MyRoomDatabase;
import com.almuslim.elaislami.RoomDBManager.RoomDBModel.AyahDBModel;
import com.almuslim.elaislami.RoomDBManager.DAO.AyahDAO;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Here is the repository for Ayah list
 */
public class AyahListRepository {

    // Make a reference for the dao
    private final AyahDAO mAyahDao;

    // Make a live data list to keep updates of the current list contents
    private LiveData<List<AyahDBModel>> mAllAyahs;

    // Make a call of the type AyahFirstResponse that will map returned data from API after calling api method
    private Call<AyahFirstResponse> call;

    // Make a list to store the data from API
    private List<AyahDBModel> mAllAyahsList;

    public static final String PREFS_NAME = "MyPreferenceFile";
    private final int surahNumber;

    // Here is the constructor
    public AyahListRepository(Application application) {

        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        /*
         * Make a shared preference to store surah number
         */
        SharedPreferences settings = application.getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
        surahNumber = settings.getInt("surahNumber",1);

        if(mAllAyahs == null){
            // Make a retrofit to call builder to get data from API
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.alquran.cloud/v1/surah/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            // Make a JSON placeholder API reference to JsonPlaceHolderAPI interface to access API methods
            JsonPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
            call = jsonPlaceHolderAPI.getAyas(surahNumber);

        }
        mAyahDao = db.ayahDAO();
        mAllAyahs = mAyahDao.getAllAyahs();
        mAllAyahs = mAyahDao.getSpecificAyahs(surahNumber);
    }

    /*
     * Here is the function to get the data from API
     */
    public void getAllAyahsApi(){

        // Asynchronously send the request and notify callback of its response or if an error occurred talking to the server, creating the request, or processing the response
        call.enqueue(new Callback<AyahFirstResponse>()
        {
            @Override
            public void onResponse(@NonNull Call<AyahFirstResponse> call, @NonNull Response<AyahFirstResponse> response)
            {
                if (!response.isSuccessful()){
                    Log.d("MVVMX", "--- Not successful");
                } else {
                    AyahFirstResponse mAllAyahsRes = response.body();
                    assert mAllAyahsRes != null;
                    mAllAyahsList=mAllAyahsRes.getData().getAyahs();

                    // Looping over the list got from the API response body
                    for (AyahDBModel ayahDBModel:mAllAyahsList)
                    {
                        ayahDBModel.setSurahNumber(surahNumber);
                        // Inserting in the ROOM DB
                        insert(ayahDBModel);
                    }
                }
            }
            // OnFailure which get the data from ROOM DB in case of lost internet connection
            @Override
            public void onFailure(@NonNull Call<AyahFirstResponse> call, @NonNull Throwable t) {
                mAllAyahs = mAyahDao.getSpecificAyahs(surahNumber);
                Log.d("MVVMX", "--- FAILED " + t.getMessage());
            }
        });
    }

    // Here is the function to get all ayahs from ROOM DB
    public LiveData<List<AyahDBModel>> getAllAyahs() {
        return mAllAyahs;
    }

    // Here is the function to insert ayah in ROOM DB
    public void insert (AyahDBModel ayahDBModel) {
        new AyahListRepository.insertAsyncTask(mAyahDao).execute(ayahDBModel);
    }

    /*
     * Here is the function to get insert surah Asynchronously in ROOM DB
     */
    private static class insertAsyncTask extends AsyncTask<AyahDBModel, Void, Void> {

        private final AyahDAO mAsyncTaskDao;

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
