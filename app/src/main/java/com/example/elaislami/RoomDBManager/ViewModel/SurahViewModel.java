package com.example.elaislami.RoomDBManager.ViewModel;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.elaislami.RoomDBManager.Repository.AyahListRepository;
import com.example.elaislami.RoomDBManager.Repository.SurahListRepository;
import com.example.elaislami.RoomDBManager.Repository.TodoListRepository;
import com.example.elaislami.RoomDBManager.RoomDBModels.AyahDBModel;
import com.example.elaislami.RoomDBManager.RoomDBModels.SurahDBModel;
import com.example.elaislami.RoomDBManager.RoomDBModels.TodoItemDBModel;

import java.util.List;

/*
 * Here is the class Surah view model, to manage surah functions
 */
public class SurahViewModel extends AndroidViewModel {

    /*
     * Livedata lists used
     */
    private final LiveData<List<SurahDBModel>> surahList;


    // Public constructor
    public SurahViewModel(Application application) {
        super(application);

        /*
         * List of repositories used
         */
        SurahListRepository sRepository = new SurahListRepository(application);

        surahList = sRepository.getAllSurahs();
        sRepository.getAllSurahsApi();
    }

    // Here is the function that returns the live data of all Surahs
    public LiveData<List<SurahDBModel>> getAllSurahs() {
        return surahList;
    }


}