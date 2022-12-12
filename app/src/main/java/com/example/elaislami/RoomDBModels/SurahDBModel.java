package com.example.elaislami.RoomDBModels;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "surah_table")
public class SurahDBModel {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "number")
    @SerializedName("number")
    private int number;

    @NonNull
    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;

    @NonNull
    @ColumnInfo(name = "englishName")
    @SerializedName("englishName")
    private String englishName;

    public SurahDBModel(int number, String name, String englishName) {
        this.number = number;
        this.name = name;
        this.englishName = englishName;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
