package com.almuslim.elaislami.RoomDBManager.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.almuslim.elaislami.RoomDBManager.Repository.TodoListRepository;
import com.almuslim.elaislami.RoomDBManager.RoomDBModel.TodoItemDBModel;

import java.util.List;

/*
 * Here is the class todo list view model, to manage todo models functions
 */
public class TodoListViewModel extends AndroidViewModel {

    // Repository used
    private final TodoListRepository tRepository;

    /*
     * Livedata lists used
     */
    private final LiveData<List<TodoItemDBModel>> todoItems;


    // Public constructor
    public TodoListViewModel(Application application) {
        super(application);

        /*
         * List of repositories used
         */
        tRepository = new TodoListRepository(application);
        todoItems = tRepository.getAllTodoList();
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