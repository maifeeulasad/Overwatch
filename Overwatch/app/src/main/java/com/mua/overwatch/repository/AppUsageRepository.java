package com.mua.overwatch.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mua.overwatch.dao.PackageUsageDao;
import com.mua.overwatch.database.ApplicationDatabase;
import com.mua.overwatch.entity.PackageUsage;

import java.util.List;

public class AppUsageRepository {
    private PackageUsageDao packageUsageDao;
    private LiveData<List<PackageUsage>> appUsageList;

    public AppUsageRepository(Application application) {
        ApplicationDatabase db = ApplicationDatabase.getInstance(application);
        packageUsageDao = db.appUsageDao();
        appUsageList = packageUsageDao.getAll();
    }

    public LiveData<List<PackageUsage>> getAppUsageList() {
        return appUsageList;
    }

    public void insert(PackageUsage packageUsage) {
        new InsertAsyncTask(packageUsageDao).execute(packageUsage);
    }

    private static class InsertAsyncTask extends AsyncTask<PackageUsage, Void, Void> {

        private PackageUsageDao packageUsageDao;

        InsertAsyncTask(PackageUsageDao packageUsageDao) {
            this.packageUsageDao = packageUsageDao;
        }

        @Override
        protected Void doInBackground(final PackageUsage... params) {
            packageUsageDao.insert(params[0]);
            return null;
        }
    }
}