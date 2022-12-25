package com.almuslim.elaislami.RoomDBManager.DAO;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.almuslim.elaislami.RoomDBManager.RoomDBModel.PrayerStatisticsDBModel;

import java.util.List;

/*
 * Here is the Dao for Prayer Statistics table
 */
@Dao
public interface PrayerStatsDAO {

    // To insert an item
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(PrayerStatisticsDBModel prayerStatsModel);

    // To delete all items
    @Query("DELETE FROM prayer_stats_table")
    void deleteAll();

    // To get all items
    @Query("SELECT * from prayer_stats_table")
    LiveData<List<PrayerStatisticsDBModel>> getAllPrayerStats();

    // To update an item
    @Update
    void update(PrayerStatisticsDBModel prayerStatsModel);

}
