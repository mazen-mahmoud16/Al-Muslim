package com.example.elaislami.RoomDB;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.elaislami.DAO.SurahDAO;
import com.example.elaislami.RoomDBModels.SurahDBModel;

@Database(entities = {SurahDBModel.class}, version = 1, exportSchema = false)

public abstract class SurahRoomDatabase extends RoomDatabase {

    public abstract SurahDAO surahDAO();
    private static SurahRoomDatabase INSTANCE;

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

    private static RoomDatabase.Callback sRoomDatabaseCallback =
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

        PopulateDbAsync(SurahRoomDatabase db) {
            sDao = db.surahDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            //sDao.deleteAll();
            return null;
        }
    }
}

