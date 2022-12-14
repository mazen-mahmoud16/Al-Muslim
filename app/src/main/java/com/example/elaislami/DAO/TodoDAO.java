package com.example.elaislami.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.elaislami.RoomDBModels.SurahDBModel;
import com.example.elaislami.RoomDBModels.TodoItemDBModel;

import java.util.List;

@Dao
public interface TodoDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TodoItemDBModel todoItemDBModel);

    @Query("DELETE FROM todo_item_table")
    void deleteAll();

    @Query("SELECT * from todo_item_table")
    LiveData<List<TodoItemDBModel>> getAlltodoItems();

    @Query("DELETE FROM todo_item_table WHERE todo_id=(:todoId)")
    void deleteTodo(int todoId);
}
