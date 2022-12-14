package com.example.elaislami.RoomDBModels;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "todo_item_table")
public class TodoItemDBModel {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "todo_id")
    private int id;

    @NonNull
    @ColumnInfo(name = "content")
    private String content;

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
