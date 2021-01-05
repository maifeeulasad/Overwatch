package com.mua.overwatch.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.mua.overwatch.converter.DateConverter;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

@Entity(tableName = "app_usage")
@TypeConverters(DateConverter.class)
public class AppUsage {
    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "app_id")
    private String appId;

    @ColumnInfo(name = "app_name")
    private String appName;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "duration")
    private Long seconds;

    @ColumnInfo(name = "icon",typeAffinity = ColumnInfo.BLOB)
    private byte[] icon;

    public AppUsage() {
        //this.date = Calendar.getInstance().getTime();
    }

    public AppUsage(String appId, String appName, Date date, Long seconds,byte[] icon) {
        this.appId = appId;
        this.appName = appName;
        this.date = date;
        this.seconds = seconds;
        this.icon = icon;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
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

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }
}