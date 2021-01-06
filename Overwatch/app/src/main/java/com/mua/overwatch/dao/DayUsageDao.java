package com.mua.overwatch.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mua.overwatch.entity.DayUsage;
import com.mua.overwatch.entity.PackageUsage;

import java.util.List;

@Dao
public interface DayUsageDao {

    @Query("SELECT * FROM DAY_USAGE")
    LiveData<List<DayUsage>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DayUsage dayUsage);
}