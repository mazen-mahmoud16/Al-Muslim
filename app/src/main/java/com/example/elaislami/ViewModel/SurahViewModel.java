package com.example.elaislami.ViewModel;
import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import com.example.elaislami.Model.SurahsModel;
import com.example.elaislami.Repository.SurahListRepository;

import java.util.ArrayList;
import java.util.List;

public class SurahViewModel extends AndroidViewModel {

    private SurahListRepository sRepository;
    private ArrayList<SurahsModel> surahList;

    public SurahViewModel (Application application) {
        super(application);
        sRepository = new SurahListRepository(application);
        surahList=sRepository.getAllSurahs();
    }

    public ArrayList<SurahsModel> getSurahList() {
        return surahList;
    }
}