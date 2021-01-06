package com.mua.overwatch.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mua.overwatch.entity.PackageUsage;

import java.util.List;

@Dao
public interface PackageUsageDao {

    @Query("SELECT * FROM PACKAGE_USAGE")
    LiveData<List<PackageUsage>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PackageUsage packageUsage);
}