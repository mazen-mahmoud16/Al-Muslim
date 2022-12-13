package com.example.elaislami.RoomDBModels;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ayah_table",primaryKeys = {"numberInSurah","surahNumber"})
public class AyahDBModel
{

    @NonNull
    @ColumnInfo(name = "numberInSurah")
    @SerializedName("numberInSurah")
    private int numberInSurah;


    @NonNull
    @ColumnInfo(name = "text")
    @SerializedName("text")
    private String text;


    @NonNull
    @ColumnInfo(name = "surahNumber")
    private int surahNumber;

    public AyahDBModel(int numberInSurah, @NonNull String text,int surahNumber) {
        this.numberInSurah = numberInSurah;
        this.text = text;
        this.surahNumber = surahNumber;
    }

    public void setSurahNumber(int surahNumber) {
        this.surahNumber = surahNumber;
    }

    public int getNumberInSurah() {
        return numberInSurah;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public int getSurahNumber() {
        return surahNumber;
    }
}
