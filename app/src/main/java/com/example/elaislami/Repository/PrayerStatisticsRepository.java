package com.example.elaislami.Repository;
import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.elaislami.DAO.PrayerStatsDAO;
import com.example.elaislami.RoomDB.SurahRoomDatabase;
import com.example.elaislami.RoomDBModels.PrayerStatisticsDBModel;
import java.util.List;

/*
 * This is the repository for prayer statistics
 */
public class PrayerStatisticsRepository {

    // Make a reference for the prayer statistics dao
    private final PrayerStatsDAO mPrayerStatsDao;
    // Make a live data list to keep updates of the current list contents
    private final LiveData<List<PrayerStatisticsDBModel>> mAllstats;

    // Here is the constructor
    public PrayerStatisticsRepository(Application application) {
        SurahRoomDatabase db = SurahRoomDatabase.getDatabase(application);
        mPrayerStatsDao = db.prayerStatsDAO();
        mAllstats = mPrayerStatsDao.getAllPrayerStats();
    }

    // Here is the function that returns the prayer statistics items
    public LiveData<List<PrayerStatisticsDBModel>> getAllPrayerStats() {
        return mAllstats;
    }

    // Here is the function that deletes all prayer statistics items
    public void deleteAllPrayer () {
        new PrayerStatisticsRepository.deleteAsyncTask(mPrayerStatsDao).execute();
    }

    // Here is the function to insert a new prayer statistics
    public void insert (PrayerStatisticsDBModel prayerStatisticsDBModel) {
        new PrayerStatisticsRepository.insertAsyncTask(mPrayerStatsDao).execute(prayerStatisticsDBModel);
    }

    // Here is the function to update an existing prayer statistics
    public void update (PrayerStatisticsDBModel prayerStatisticsDBModel) {
        new PrayerStatisticsRepository.updateAsyncTask(mPrayerStatsDao).execute(prayerStatisticsDBModel);
    }

    /*
     * Here is the function to delete a prayer statistics item asynchronously
     */
    private static class deleteAsyncTask extends AsyncTask {

        private final PrayerStatsDAO mAsyncTaskDao;

        deleteAsyncTask(PrayerStatsDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            mAsyncTaskDao.deleteAll();

            return null;
        }
    }

    /*
     * Here is the function to insert a prayer statistics item asynchronously
     */
    private static class insertAsyncTask extends AsyncTask<PrayerStatisticsDBModel, Void, Void> {

        private PrayerStatsDAO mAsyncTaskDao;

        insertAsyncTask(PrayerStatsDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final PrayerStatisticsDBModel... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /*
     * Here is the function to update a prayer statistics item asynchronously
     */
    private static class updateAsyncTask extends AsyncTask<PrayerStatisticsDBModel, Void, Void> {

        private PrayerStatsDAO mAsyncTaskDao;

        updateAsyncTask(PrayerStatsDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final PrayerStatisticsDBModel... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }


}
