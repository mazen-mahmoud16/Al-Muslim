package com.example.elaislami.RoomDBManager.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.elaislami.RoomDBManager.Repository.AyahListRepository;
import com.example.elaislami.RoomDBManager.Repository.SurahListRepository;
import com.example.elaislami.RoomDBManager.RoomDBModels.AyahDBModel;
import com.example.elaislami.RoomDBManager.RoomDBModels.SurahDBModel;

import java.util.List;

/*
 * Here is the class Ayah view model, to manage ayahs functions
 */
public class AyahViewModel extends AndroidViewModel {

    /*
     * Livedata lists used
     */
    private final LiveData<List<AyahDBModel>> ayahList;


    // Public constructor
    public AyahViewModel(Application application) {
        super(application);

        /*
         * List of repositories used
         */
        AyahListRepository aRepository = new AyahListRepository(application);

        ayahList = aRepository.getAllAyahs();
        aRepository.getAllAyahsApi();
    }

    // Here is the function that returns the live data of all Ayahs
    public LiveData<List<AyahDBModel>> getAllAyahs() {
        return ayahList;
    }


}