package com.example.elaislami.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.elaislami.DAO.TodoDAO;
import com.example.elaislami.RoomDB.SurahRoomDatabase;
import com.example.elaislami.RoomDBModels.TodoItemDBModel;

import java.util.List;

/*
 * Here is the repository for todolist
 */
public class TodoListRepository {

    // Make a reference for the dao
    private final TodoDAO todoDAO;
    // Make a live data list to keep updates of the current list contents
    private final LiveData<List<TodoItemDBModel>> todoList;

    // Here is the constructor
    public TodoListRepository(Application application) {
        SurahRoomDatabase db = SurahRoomDatabase.getDatabase(application);
        todoDAO = db.todoDAO();
        todoList = todoDAO.getAlltodoItems();
    }

    // Here is the function that returns the todo list items
    public LiveData<List<TodoItemDBModel>> getAllTodoList() {
        return todoList;
    }

    // Here is the function to insert a todo item
    public void insert (TodoItemDBModel todoItemDBModel) {
        new TodoListRepository.insertAsyncTask(todoDAO).execute(todoItemDBModel);
    }

    // Here is the function to delete a todo item
    public void delete (int todoId) {
        new TodoListRepository.deleteAsyncTask(todoDAO).execute(todoId);
    }

    /*
     * Here is the function to insert a todo item asynchronously
     */
    private static class insertAsyncTask extends AsyncTask<TodoItemDBModel, Void, Void> {

        private final TodoDAO mAsyncTaskDao;

        insertAsyncTask(TodoDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TodoItemDBModel... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    /*
     * Here is the function to delete a todo item asynchronously
     */
    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {

        private final TodoDAO mAsyncTaskDao;

        deleteAsyncTask(TodoDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deleteTodo(params[0]);
            return null;
        }
    }
}
