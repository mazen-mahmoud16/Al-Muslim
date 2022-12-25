package com.almuslim.elaislami.RoomDBManager.RoomDB;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.almuslim.elaislami.RoomDBManager.DAO.TodoDAO;
import com.almuslim.elaislami.RoomDBManager.RoomDBModel.SurahDBModel;
import com.almuslim.elaislami.RoomDBManager.DAO.AyahDAO;
import com.almuslim.elaislami.RoomDBManager.DAO.PrayerStatsDAO;
import com.almuslim.elaislami.RoomDBManager.DAO.SurahDAO;
import com.almuslim.elaislami.RoomDBManager.RoomDBModel.AyahDBModel;
import com.almuslim.elaislami.RoomDBManager.RoomDBModel.PrayerStatisticsDBModel;
import com.almuslim.elaislami.RoomDBManager.RoomDBModel.TodoItemDBModel;

/*
 * Here is the Room Database where we initialize our project's database
 */

@Database(entities = {SurahDBModel.class, AyahDBModel.class, TodoItemDBModel.class, PrayerStatisticsDBModel.class}, version = 7, exportSchema = false)
public abstract class MyRoomDatabase extends RoomDatabase {

    /*
     * List of DAOs used
     */
    public abstract SurahDAO surahDAO();
    public abstract AyahDAO ayahDAO();
    public abstract TodoDAO todoDAO();
    public abstract PrayerStatsDAO prayerStatsDAO();

    /*
     * ROOM Database instance
     */
    private static MyRoomDatabase INSTANCE;

    /*
     * Here is ROOM Database Constructor
     */

    public static MyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MyRoomDatabase.class, "surah_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final SurahDAO sDao;
        private final AyahDAO aDao;
        private final TodoDAO tDao;
        private final PrayerStatsDAO pDao;


        PopulateDbAsync(MyRoomDatabase db) {
            sDao = db.surahDAO();
            aDao = db.ayahDAO();
            tDao = db.todoDAO();
            pDao=db.prayerStatsDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            /*
            sDao.deleteAll();
            aDao.deleteAll();
            tDao.deleteAll();
            pDao.deleteAll();
            */
            return null;
        }
    }
}

