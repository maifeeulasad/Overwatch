package com.mua.overwatch.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mua.overwatch.dao.DayUsageDao;
import com.mua.overwatch.dao.PackageUsageDao;
import com.mua.overwatch.database.ApplicationDatabase;
import com.mua.overwatch.entity.DayUsage;
import com.mua.overwatch.entity.PackageUsage;

import java.util.List;

public class DayUsageRepository {
    private DayUsageDao dayUsageDao;
    private LiveData<List<DayUsage>> dayUsageList;

    public DayUsageRepository(Application application) {
        ApplicationDatabase db = ApplicationDatabase.getInstance(application);
        dayUsageDao = db.dayUsageDao();
        dayUsageList = dayUsageDao.getAll();
    }

    public LiveData<List<DayUsage>> getDayUsageList() {
        return dayUsageList;
    }

    public void insert(DayUsage dayUsage) {
        new InsertAsyncTask(dayUsageDao).execute(dayUsage);
    }

    private static class InsertAsyncTask extends AsyncTask<DayUsage, Void, Void> {

        private DayUsageDao dayUsageDao;

        InsertAsyncTask(DayUsageDao dayUsageDao) {
            this.dayUsageDao = dayUsageDao;
        }

        @Override
        protected Void doInBackground(final DayUsage... params) {
            dayUsageDao.insert(params[0]);
            return null;
        }
    }
}