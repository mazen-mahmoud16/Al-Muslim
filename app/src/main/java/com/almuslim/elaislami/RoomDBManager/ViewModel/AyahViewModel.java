package com.almuslim.elaislami.RoomDBManager.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.almuslim.elaislami.RoomDBManager.Repository.AyahListRepository;
import com.almuslim.elaislami.RoomDBManager.RoomDBModel.AyahDBModel;

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