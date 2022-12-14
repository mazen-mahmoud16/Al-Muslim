package com.example.elaislami.ViewModel;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.elaislami.Repository.AyahListRepository;
import com.example.elaislami.Repository.SurahListRepository;
import com.example.elaislami.Repository.TodoListRepository;
import com.example.elaislami.RoomDBModels.AyahDBModel;
import com.example.elaislami.RoomDBModels.SurahDBModel;
import com.example.elaislami.RoomDBModels.TodoItemDBModel;

import java.util.List;

public class SurahViewModel extends AndroidViewModel {

    private SurahListRepository sRepository;
    private AyahListRepository aRepository;
    private TodoListRepository tRepository;
    private LiveData<List<SurahDBModel>> surahList;
    private LiveData<List<AyahDBModel>> ayahList;
    private LiveData<List<TodoItemDBModel>> todoItems;


    public SurahViewModel(Application application) {
        super(application);
        sRepository = new SurahListRepository(application);
        aRepository = new AyahListRepository(application);
        tRepository = new TodoListRepository(application);
        surahList = sRepository.getAllSurahs();
        ayahList = aRepository.getAllAyahs();
        todoItems = tRepository.getAllTodoList();
        sRepository.getAllSurahsApi();
        aRepository.getAllAyahsApi();
    }

    public LiveData<List<SurahDBModel>> getAllSurahs() {
        return surahList;
    }
    public LiveData<List<AyahDBModel>> getAllAyahs() {
        return ayahList;
    }
    public LiveData<List<TodoItemDBModel>> getAllTodoList() {
        return todoItems;
    }

    public void insertTodoItem(TodoItemDBModel todoItem) { tRepository.insert(todoItem); }
    public void deleteTodoItem(int todoId) {
        tRepository.delete(todoId);
    }

}