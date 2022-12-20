package com.example.elaislami.RoomDBModels;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "prayer_stats_table")

public class PrayerStatisticsDBModel {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "prayer_stat_id")
    private int id;


    @NonNull
    @ColumnInfo(name = "date")
    private String date;

    @NonNull
    @ColumnInfo(name = "fajr")
    boolean fajr;

    @NonNull
    @ColumnInfo(name = "dhuhr")
    boolean dhuhr;

    @NonNull
    @ColumnInfo(name = "asr")
    boolean asr;

    @NonNull
    @ColumnInfo(name = "maghrib")
    boolean maghrib;

    @NonNull
    @ColumnInfo(name = "isha")
    boolean isha;

    @NonNull
    public String getDate() {
        return date;
    }

    public boolean isFajr() {
        return fajr;
    }

    public boolean isDhuhr() {
        return dhuhr;
    }

    public boolean isAsr() {
        return asr;
    }

    public boolean isMaghrib() {
        return maghrib;
    }

    public boolean isIsha() {
        return isha;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public void setFajr(boolean fajr) {
        this.fajr = fajr;
    }

    public void setDhuhr(boolean dhuhr) {
        this.dhuhr = dhuhr;
    }

    public void setAsr(boolean asr) {
        this.asr = asr;
    }

    public void setMaghrib(boolean maghrib) {
        this.maghrib = maghrib;
    }

    public void setIsha(boolean isha) {
        this.isha = isha;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public PrayerStatisticsDBModel(@NonNull String date, boolean fajr, boolean dhuhr, boolean asr, boolean maghrib, boolean isha) {
        this.date = date;
        this.fajr = fajr;
        this.dhuhr = dhuhr;
        this.asr = asr;
        this.maghrib = maghrib;
        this.isha = isha;
    }
}
