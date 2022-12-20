package com.example.elaislami.Repository;
import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.elaislami.DAO.PrayerStatsDAO;
import com.example.elaislami.RoomDB.SurahRoomDatabase;
import com.example.elaislami.RoomDBModels.PrayerStatisticsDBModel;
import java.util.List;


public class PrayerStatisticsRepository {

    private PrayerStatsDAO mPrayerStatsDao;
    private LiveData<List<PrayerStatisticsDBModel>> mAllstats;

    public PrayerStatisticsRepository(Application application) {
        SurahRoomDatabase db = SurahRoomDatabase.getDatabase(application);
        mPrayerStatsDao = db.prayerStatsDAO();
        mAllstats = mPrayerStatsDao.getAllPrayerStats();
    }

    public LiveData<List<PrayerStatisticsDBModel>> getAllPrayerStats() {
        return mAllstats;
    }



    public void deleteAllPrayer () {
        new PrayerStatisticsRepository.deleteAsyncTask(mPrayerStatsDao).execute();
    }


    public void insert (PrayerStatisticsDBModel prayerStatisticsDBModel) {
        new PrayerStatisticsRepository.insertAsyncTask(mPrayerStatsDao).execute(prayerStatisticsDBModel);
    }
    public void update (PrayerStatisticsDBModel prayerStatisticsDBModel) {
        new PrayerStatisticsRepository.updateAsyncTask(mPrayerStatsDao).execute(prayerStatisticsDBModel);
    }

    private static class deleteAsyncTask extends AsyncTask {

        private PrayerStatsDAO mAsyncTaskDao;

        deleteAsyncTask(PrayerStatsDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            mAsyncTaskDao.deleteAll();

            return null;
        }
    }


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
