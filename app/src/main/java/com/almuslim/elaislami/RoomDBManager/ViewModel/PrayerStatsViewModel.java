package com.almuslim.elaislami.RoomDBManager.ViewModel;
import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.almuslim.elaislami.RoomDBManager.Repository.PrayerStatisticsRepository;
import com.almuslim.elaislami.RoomDBManager.RoomDBModel.PrayerStatisticsDBModel;
import java.util.List;

/*
 * Here is the class prayer statistics view model, to manage prayer statistics functions
 */
public class PrayerStatsViewModel extends AndroidViewModel {

    /*
     * List of repositories used
     */
    private final PrayerStatisticsRepository pRepository;

    /*
     * Live data lists used
     */
    private final LiveData<List<PrayerStatisticsDBModel>> prayerStatsList;

    /*
     * Public Constructor
     */
    public PrayerStatsViewModel(Application application) {
        super(application);
        pRepository = new PrayerStatisticsRepository(application);
        prayerStatsList = pRepository.getAllPrayerStats();
    }

    // Here is the function that returns the livedata of all prayer statistics
    public LiveData<List<PrayerStatisticsDBModel>> getAllPrayerStatsList() {
        return prayerStatsList;
    }

    // Here is the function that insert prayer statistics
    public void insertPrayerStatistics(PrayerStatisticsDBModel prayerStatisticsDBModel) { pRepository.insert(prayerStatisticsDBModel); }

    // Here is the function that update prayer statistics
    public void updatePrayerStatistics(PrayerStatisticsDBModel prayerStatisticsDBModel) { pRepository.update(prayerStatisticsDBModel); }

    // Here is the function that delete all prayer statistics
    public void deleteAllPrayerStatistics() { pRepository.deleteAllPrayer(); }

}
