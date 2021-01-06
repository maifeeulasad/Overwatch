package com.mua.overwatch.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.mua.overwatch.converter.DateConverter;

import java.sql.Date;

@Entity(tableName = "package_usage")
@TypeConverters(DateConverter.class)
public class PackageUsage {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "package_usage_id")
    private Long packageUsageId;

    @ColumnInfo(name = "package_id")
    private String packageId;

    @ColumnInfo(name = "app_name")
    private String packageName;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "duration")
    private Long duration;

    @ColumnInfo(name = "icon", typeAffinity = ColumnInfo.BLOB)
    private byte[] icon;

    public PackageUsage(String packageId, String packageName,
                        Date date, Long duration, byte[] icon) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.date = date;
        this.duration = duration;
        this.icon = icon;
    }

    public Long getPackageUsageId() {
        return packageUsageId;
    }

    public void setPackageUsageId(Long packageUsageId) {
        this.packageUsageId = packageUsageId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
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
}