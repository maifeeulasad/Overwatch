package com.mua.overwatch.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mua.overwatch.dao.AppUsageDao;
import com.mua.overwatch.entity.AppUsage;

@Database(entities = {AppUsage.class}, version = 1)
public abstract class ApplicationDatabase extends RoomDatabase {
    private static volatile ApplicationDatabase INSTANCE;

    public abstract AppUsageDao appUsageDao();

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