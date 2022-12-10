package com.example.elaislami.ViewModel;
import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.elaislami.Repository.SurahListRepository;
import com.example.elaislami.RoomDBModels.SurahDBModel;

import java.util.List;

public class SurahViewModel extends AndroidViewModel {

    private SurahListRepository sRepository;
    private LiveData<List<SurahDBModel>> surahList;

    public SurahViewModel(Application application) {
        super(application);
        sRepository = new SurahListRepository(application);
        surahList = sRepository.getAllSurahs();
        if(sRepository.getAllSurahs() == null){
            sRepository.getAllSurahsApi();
        }

    }

    public LiveData<List<SurahDBModel>> getAllSurahs() {
        return surahList;
    }

}