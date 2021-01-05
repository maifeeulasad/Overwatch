package com.mua.overwatch.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mua.overwatch.dao.AppUsageDao;
import com.mua.overwatch.database.ApplicationDatabase;
import com.mua.overwatch.entity.AppUsage;

import java.util.List;

public class AppUsageRepository {
    private AppUsageDao appUsageDao;
    private LiveData<List<AppUsage>> appUsageList;

    public AppUsageRepository(Application application) {
        ApplicationDatabase db = ApplicationDatabase.getInstance(application);
        appUsageDao = db.appUsageDao();
        appUsageList = appUsageDao.getAll();
    }

    public LiveData<List<AppUsage>> getAppUsageList() {
        return appUsageList;
    }

    public void insert(AppUsage appUsage) {
        new InsertAsyncTask(appUsageDao).execute(appUsage);
    }

    private static class InsertAsyncTask extends AsyncTask<AppUsage, Void, Void> {

        private AppUsageDao appUsageDao;

        InsertAsyncTask(AppUsageDao appUsageDao) {
            this.appUsageDao = appUsageDao;
        }

        @Override
        protected Void doInBackground(final AppUsage... params) {
            appUsageDao.insert(params[0]);
            return null;
        }
    }
}