package com.mua.overwatch.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import com.mua.overwatch.converter.DateConverter;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "day_usage",
        foreignKeys = @ForeignKey(entity = PackageUsage.class,
        parentColumns = "package_usage_id",
        childColumns = "day_usage_id",
        onUpdate = CASCADE))
@TypeConverters(DateConverter.class)
public class DayUsage {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "day_usage_id")
    private Long dayUsageId;

    @ColumnInfo(name = "date")
    private Date date;

    //@Relation(parentColumn = "day_usage_id", entityColumn = "package_id", entity = PackageUsage.class)
    //private List<PackageUsage> packageUsageList;


    /*
    public DayUsage(int dayMinus, List<PackageUsage> packageUsageList) {
        this(minusDay(dayMinus), packageUsageList);
    }
     */

    public DayUsage(Date date) {
        this.date = date;
    }


    private static Date addDay(int toAdd){
        Calendar current = Calendar.getInstance();
        current.set(Calendar.HOUR,0);
        current.set(Calendar.MINUTE,0);
        current.set(Calendar.SECOND,0);
        current.add(Calendar.HOUR,toAdd);
        return new Date(current.getTime().getTime());
    }

    private static Date minusDay(int toMinus){
        return addDay(-toMinus);
    }

    public Long getDayUsageId() {
        return dayUsageId;
    }

    public void setDayUsageId(Long dayUsageId) {
        this.dayUsageId = dayUsageId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /*
    public List<PackageUsage> getPackageUsageList() {
        return packageUsageList;
    }

    public void setPackageUsageList(List<PackageUsage> packageUsageList) {
        this.packageUsageList = packageUsageList;
    }
    */
}