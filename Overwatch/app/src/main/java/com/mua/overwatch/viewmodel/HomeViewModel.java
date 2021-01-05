package com.mua.overwatch.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mua.overwatch.entity.AppUsage;
import com.mua.overwatch.repository.AppUsageRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private AppUsageRepository appUsageRepository;
    private LiveData<List<AppUsage>> appUsageList;
    
    public HomeViewModel(@NonNull Application application) {
        super(application);
        appUsageRepository = new AppUsageRepository(application);
        appUsageList = appUsageRepository.getAppUsageList();
    }
    

    public LiveData<List<AppUsage>> getAppUsageList() {
        return appUsageList;
    }

    public void insert(AppUsage appUsage) {
        appUsageRepository.insert(appUsage);
    }

    public View.OnClickListener addRandom(){
        return v -> insert(new AppUsage());
    }
    
}
