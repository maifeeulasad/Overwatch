package com.mua.overwatch.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mua.overwatch.dao.PackageUsageDao;
import com.mua.overwatch.entity.DayUsage;
import com.mua.overwatch.entity.PackageUsage;

@Database(entities = {PackageUsage.class, DayUsage.class}, version = 1)
public abstract class ApplicationDatabase extends RoomDatabase {
    private static volatile ApplicationDatabase INSTANCE;

    public abstract PackageUsageDao appUsageDao();

    public static ApplicationDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized(ApplicationDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ApplicationDatabase.class, "overwatch.db").build();
                }
            }
        }
        return INSTANCE;
    }
}