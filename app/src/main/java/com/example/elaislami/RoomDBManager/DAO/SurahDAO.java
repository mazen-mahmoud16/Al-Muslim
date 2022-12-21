package com.example.elaislami.RoomDBManager.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.elaislami.RoomDBManager.RoomDBModel.SurahDBModel;

import java.util.List;

/*
 * Here is the Dao for Surah table
 */
@Dao
public interface SurahDAO {

    // To insert an item
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SurahDBModel surahModel);

    // To delete all items
    @Query("DELETE FROM surah_table")
    void deleteAll();

    // To get all surah items
    @Query("SELECT * from surah_table ORDER BY number ASC")
    LiveData<List<SurahDBModel>> getAllSurahs();
}
