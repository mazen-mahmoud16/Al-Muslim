package com.almuslim.elaislami.RoomDBManager.RoomDBModel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
/*
 * Here is the TodoItem database model for todo_item_table, to hold the table's columns
 */

@Entity(tableName = "todo_item_table")
public class TodoItemDBModel {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "todo_id")
    private int id;

    @NonNull
    @ColumnInfo(name = "content")
    private String content;

    /*
     * Here are the setters and getters for the table
     */
    public int getId() {
        return id;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }
}
