package com.mua.overwatch.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.mua.overwatch.converter.DateConverter;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;

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
    private Long duration;

    @ColumnInfo(name = "icon", typeAffinity = ColumnInfo.BLOB)
    private byte[] icon;

    @ColumnInfo(name = "count")
    private Integer launchCount;

    public AppUsage() {
        this.launchCount = 0;
        this.duration = 0L;
        //this.date = Calendar.getInstance().getTime();
    }

    public AppUsage(String appId) {
        this.appId = appId;
    }

    public AppUsage(String appId, String appName, Date date, Long duration, byte[] icon) {
        this.appId = appId;
        this.appName = appName;
        this.date = date;
        this.duration = duration;
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

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public Integer getLaunchCount() {
        return launchCount;
    }

    public void setLaunchCount(Integer launchCount) {
        this.launchCount = launchCount;
    }
}