package com.almuslim.elaislami.RoomDBManager.RoomDBModel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/*
 * Here is the surah database model for surah_table, to hold the table's columns
 */
@Entity(tableName = "surah_table")
public class SurahDBModel {

    @PrimaryKey
    @ColumnInfo(name = "number")
    @SerializedName("number")
    private final int number;

    @NonNull
    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;

    @NonNull
    @ColumnInfo(name = "englishName")
    @SerializedName("englishName")
    private final String englishName;

    // Here is the constructor for surah database model
    public SurahDBModel(int number, @NonNull String name, @NonNull String englishName) {
        this.number = number;
        this.name = name;
        this.englishName = englishName;
    }

    /*
     * Here are the setters and getters for the table
     */
    public int getNumber() {
        return number;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getEnglishName() {
        return englishName;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
