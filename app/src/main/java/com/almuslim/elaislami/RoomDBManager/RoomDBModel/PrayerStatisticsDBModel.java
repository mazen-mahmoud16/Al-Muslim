package com.almuslim.elaislami.RoomDBManager.RoomDBModel;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
 * Here is the Prayer Statistics database model for prayer_stats_table, to hold the table's columns
 */

@Entity(tableName = "prayer_stats_table")
public class PrayerStatisticsDBModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "prayer_stat_id")
    private int id;

    @NonNull
    @ColumnInfo(name = "date")
    private final String date;

    @ColumnInfo(name = "fajr")
    boolean fajr;

    @ColumnInfo(name = "dhuhr")
    boolean dhuhr;

    @ColumnInfo(name = "asr")
    boolean asr;

    @ColumnInfo(name = "maghrib")
    boolean maghrib;

    @ColumnInfo(name = "isha")
    boolean isha;

    /*
     * Here are the setters and getters for the table
     */

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

    // Here is the constructor for Prayer Statistics table
    public PrayerStatisticsDBModel(@NonNull String date, boolean fajr, boolean dhuhr, boolean asr, boolean maghrib, boolean isha) {
        this.date = date;
        this.fajr = fajr;
        this.dhuhr = dhuhr;
        this.asr = asr;
        this.maghrib = maghrib;
        this.isha = isha;
    }
}
