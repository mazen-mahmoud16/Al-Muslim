package com.example.elaislami.DAO;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.elaislami.RoomDBModels.PrayerStatisticsDBModel;

import java.util.List;
@Dao
public interface PrayerStatsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(PrayerStatisticsDBModel prayerStatsModel);

    @Query("DELETE FROM prayer_stats_table")
    void deleteAll();

    @Query("SELECT * from prayer_stats_table")
    LiveData<List<PrayerStatisticsDBModel>> getAllPrayerStats();


}
