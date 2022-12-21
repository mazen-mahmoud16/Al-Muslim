package com.example.elaislami.ViewModel;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.elaislami.Repository.AyahListRepository;
import com.example.elaislami.Repository.SurahListRepository;
import com.example.elaislami.Repository.TodoListRepository;
import com.example.elaislami.RoomDBModels.AyahDBModel;
import com.example.elaislami.RoomDBModels.SurahDBModel;
import com.example.elaislami.RoomDBModels.TodoItemDBModel;

import java.util.List;

/*
 * Here is the class Surah view model, to manage surah, ayah and todo models functions
 */
public class SurahViewModel extends AndroidViewModel {

    // Repository used
    private final TodoListRepository tRepository;

    /*
     * Livedata lists used
     */
    private final LiveData<List<SurahDBModel>> surahList;
    private final LiveData<List<AyahDBModel>> ayahList;
    private final LiveData<List<TodoItemDBModel>> todoItems;


    // Public constructor
    public SurahViewModel(Application application) {
        super(application);

        /*
         * List of repositories used
         */
        SurahListRepository sRepository = new SurahListRepository(application);
        AyahListRepository aRepository = new AyahListRepository(application);
        tRepository = new TodoListRepository(application);
        surahList = sRepository.getAllSurahs();
        ayahList = aRepository.getAllAyahs();
        todoItems = tRepository.getAllTodoList();
        sRepository.getAllSurahsApi();
        aRepository.getAllAyahsApi();
    }

    // Here is the function that returns the live data of all Surahs
    public LiveData<List<SurahDBModel>> getAllSurahs() {
        return surahList;
    }

    // Here is the function that returns the live data of all Ayahs
    public LiveData<List<AyahDBModel>> getAllAyahs() {
        return ayahList;
    }

    // Here is the function that returns the live data of all Todo Items
    public LiveData<List<TodoItemDBModel>> getAllTodoList() {
        return todoItems;
    }

    // Here is the function to insert a todo item
    public void insertTodoItem(TodoItemDBModel todoItem) { tRepository.insert(todoItem); }

    // Here is the function to delete a todo item
    public void deleteTodoItem(int todoId) {
        tRepository.delete(todoId);
    }

}