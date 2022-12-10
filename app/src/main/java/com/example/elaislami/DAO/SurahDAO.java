package com.example.elaislami.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.elaislami.RoomDBModels.SurahDBModel;

import java.util.List;
@Dao
public interface SurahDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SurahDBModel surahModel);

    @Query("DELETE FROM surah_table")
    void deleteAll();

    @Query("SELECT * from surah_table ORDER BY number ASC")
    LiveData<List<SurahDBModel>> getAllSurahs();
}
