package com.almuslim.elaislami.RoomDBManager.RoomDBModel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

/*
 * Here is the ayah database model for ayah_table, to hold the table's columns
*/
@Entity(tableName = "ayah_table",primaryKeys = {"numberInSurah","surahNumber"})
public class AyahDBModel
{

    @ColumnInfo(name = "numberInSurah")
    @SerializedName("numberInSurah")
    private final int numberInSurah;


    @NonNull
    @ColumnInfo(name = "text")
    @SerializedName("text")
    private final String text;


    @ColumnInfo(name = "surahNumber")
    private int surahNumber;

    public void setJuz(int juz) {
        this.juz = juz;
    }

    @ColumnInfo(name = "juz")
    private int juz;

    // Here is the constructor for ayah table
    public AyahDBModel(int numberInSurah, @NonNull String text,int surahNumber) {
        this.numberInSurah = numberInSurah;
        this.text = text;
        this.surahNumber = surahNumber;
    }

    /*
     * Here are the setters and getters for the table
     */
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

    public int getJuz() {
        return juz;
    }

    public int getSurahNumber() {
        return surahNumber;
    }
}
