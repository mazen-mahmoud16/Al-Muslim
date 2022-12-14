package com.example.elaislami.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.elaislami.DAO.TodoDAO;
import com.example.elaislami.RoomDB.SurahRoomDatabase;
import com.example.elaislami.RoomDBModels.TodoItemDBModel;

import java.util.List;

public class TodoListRepository {

    private TodoDAO todoDAO;
    private LiveData<List<TodoItemDBModel>> todoList;

    public TodoListRepository(Application application) {
        SurahRoomDatabase db = SurahRoomDatabase.getDatabase(application);
        todoDAO = db.todoDAO();
        todoList = todoDAO.getAlltodoItems();
    }

    public LiveData<List<TodoItemDBModel>> getAllTodoList() {
        return todoList;
    }

    public void insert (TodoItemDBModel todoItemDBModel) {
        new TodoListRepository.insertAsyncTask(todoDAO).execute(todoItemDBModel);
    }

    public void delete (int todoId) {
        new TodoListRepository.deleteAsyncTask(todoDAO).execute(todoId);
    }

    private static class insertAsyncTask extends AsyncTask<TodoItemDBModel, Void, Void> {

        private TodoDAO mAsyncTaskDao;

        insertAsyncTask(TodoDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TodoItemDBModel... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {

        private TodoDAO mAsyncTaskDao;

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
