package com.example.elaislami.ViewModel;
import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.elaislami.Repository.PrayerStatisticsRepository;
import com.example.elaislami.RoomDBModels.PrayerStatisticsDBModel;
import java.util.List;

public class PrayerStatsViewModel extends AndroidViewModel {
    private PrayerStatisticsRepository pRepository;
    private LiveData<List<PrayerStatisticsDBModel>> prayerStatsList;

    public PrayerStatsViewModel(Application application) {
        super(application);
        pRepository = new PrayerStatisticsRepository(application);
        prayerStatsList = pRepository.getAllPrayerStats();
    }

    public LiveData<List<PrayerStatisticsDBModel>> getAllPrayerStatsList() {
        return prayerStatsList;
    }

    public void insertPrayer(PrayerStatisticsDBModel prayerStatisticsDBModel) { pRepository.insert(prayerStatisticsDBModel); }

    public void deleteAllPrayer() { pRepository.deleteAllPrayer();}

}
