package com.example.elaislami.RoomDBManager.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.elaislami.RoomDBManager.RoomDBModel.TodoItemDBModel;

import java.util.List;

/*
 * Here is the Dao for todo table
 */
@Dao
public interface TodoDAO {

    // To insert an item
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TodoItemDBModel todoItemDBModel);

    // To delete all items
    @Query("DELETE FROM todo_item_table")
    void deleteAll();

    // Get all todo list
    @Query("SELECT * from todo_item_table")
    LiveData<List<TodoItemDBModel>> getAlltodoItems();

    // Delete a todo item
    @Query("DELETE FROM todo_item_table WHERE todo_id=(:todoId)")
    void deleteTodo(int todoId);
}
