package com.mua.overwatch.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mua.overwatch.entity.AppUsage;

import java.util.List;

@Dao
public interface AppUsageDao {

    @Query("SELECT * FROM APP_USAGE")
    LiveData<List<AppUsage>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AppUsage appUsage);
}