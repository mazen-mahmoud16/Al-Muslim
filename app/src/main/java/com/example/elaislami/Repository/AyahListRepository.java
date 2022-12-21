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

    // Make a retrofit to call builder to get data from API
    private Retrofit retrofit;

    // Make a JSON placeholder API reference to JsonPlaceHolderAPI interface to access API methods
    private JsonPlaceHolderAPI jsonPlaceHolderAPI;

    // Make a call of the type AyahFirstResponse that will map returned data from API after calling api method
    private Call<AyahFirstResponse> call;

    // Make a list to store the data from API
    private List<AyahDBModel> mAllAyahsList;

    /*
     * Make a shared preference to store surah number
     */
    private SharedPreferences settings;
    public static final String PREFS_NAME = "MyPreferenceFile";
    private int surahNumber;

    // Here is the constructor
    public AyahListRepository(Application application) {

        SurahRoomDatabase db = SurahRoomDatabase.getDatabase(application);
        settings = application.getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
        surahNumber = settings.getInt("surahNumber",1);

        if(mAllAyahs == null){
           retrofit = new Retrofit.Builder()
                   .baseUrl("https://api.alquran.cloud/v1/surah/")
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
           jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
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
        call.enqueue(new Callback<AyahFirstResponse>() {
            @Override
            public void onResponse(Call<AyahFirstResponse> call, Response<AyahFirstResponse> response)
            {
                if (!response.isSuccessful()){
                    Log.d("MVVMX", "--- Not successful");
                } else {

                    AyahFirstResponse mAllAyahsRes = response.body();
                    assert mAllAyahsRes != null;
                    mAllAyahsList=mAllAyahsRes.getData().getAyahs();

                    for (AyahDBModel ayahDBModel:mAllAyahsList)
                    {
                        ayahDBModel.setSurahNumber(surahNumber);
                        insert(ayahDBModel);
                    }
                }
            }
            @Override
            public void onFailure(Call<AyahFirstResponse> call, Throwable t) {
                mAllAyahs = mAyahDao.getAllAyahs();

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

    // Here is the function to get insert surah Asynchronously in ROOM DB
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
