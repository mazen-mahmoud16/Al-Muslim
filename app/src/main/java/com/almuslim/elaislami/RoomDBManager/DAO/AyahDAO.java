package com.almuslim.elaislami.RoomDBManager.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.almuslim.elaislami.RoomDBManager.RoomDBModel.AyahDBModel;

import java.util.List;

/*
 * Here is the Dao for Ayah table
 */
@Dao
public interface AyahDAO {

    // To insert an item
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(AyahDBModel ayahModel);

    // To delete all items
    @Query("DELETE FROM ayah_table")
    void deleteAll();

    // To get all items
    @Query("SELECT * from ayah_table")
    LiveData<List<AyahDBModel>> getAllAyahs();

    // To get all ayahs for a specific surah
    @Query("SELECT * from ayah_table Where surahNumber=(:surahNumber)")
    LiveData<List<AyahDBModel>> getSpecificAyahs(int surahNumber);
}
