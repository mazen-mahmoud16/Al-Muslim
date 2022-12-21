package com.example.elaislami.RoomDB;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.elaislami.DAO.AyahDAO;
import com.example.elaislami.DAO.PrayerStatsDAO;
import com.example.elaislami.DAO.SurahDAO;
import com.example.elaislami.DAO.TodoDAO;
import com.example.elaislami.RoomDBModels.AyahDBModel;
import com.example.elaislami.RoomDBModels.PrayerStatisticsDBModel;
import com.example.elaislami.RoomDBModels.SurahDBModel;
import com.example.elaislami.RoomDBModels.TodoItemDBModel;

/*
 * Here is the Room Database where we initialize our project's database
 */

@Database(entities = {SurahDBModel.class, AyahDBModel.class, TodoItemDBModel.class, PrayerStatisticsDBModel.class}, version = 7, exportSchema = false)
public abstract class SurahRoomDatabase extends RoomDatabase {

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
    private static SurahRoomDatabase INSTANCE;

    /*
     * Here is ROOM Database Constructor
     */

    public static SurahRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SurahRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    SurahRoomDatabase.class, "surah_database")
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


        PopulateDbAsync(SurahRoomDatabase db) {
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

