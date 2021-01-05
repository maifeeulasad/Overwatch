package com.mua.overwatch.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.mua.overwatch.converter.DateConverter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

@Entity(tableName = "app_usage")
@TypeConverters(DateConverter.class)
public class AppUsage {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "app_id")
    private int appId;

    @ColumnInfo(name = "app_name")
    private String app_name;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "duration")
    private Long seconds;

    public AppUsage() {
        //this.date = Calendar.getInstance().getTime();
    }

    public AppUsage(int appId, String app_name, Date date, Long seconds) {
        this.appId = appId;
        this.app_name = app_name;
        this.date = date;
        this.seconds = seconds;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getSeconds() {
        return seconds;
    }

    public void setSeconds(Long seconds) {
        this.seconds = seconds;
    }
}