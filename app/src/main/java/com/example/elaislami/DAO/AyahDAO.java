package com.example.elaislami.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.elaislami.RoomDBModels.AyahDBModel;

import java.util.List;

@Dao
public interface AyahDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(AyahDBModel ayahModel);

    @Query("DELETE FROM ayah_table")
    void deleteAll();

    @Query("SELECT * from ayah_table")
    LiveData<List<AyahDBModel>> getAllAyahs();

    @Query("SELECT * from ayah_table Where surahNumber=(:surahNumber)")
    LiveData<List<AyahDBModel>> getSpecificAyahs(int surahNumber);
}
