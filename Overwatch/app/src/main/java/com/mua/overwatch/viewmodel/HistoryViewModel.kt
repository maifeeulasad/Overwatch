package com.mua.overwatch.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mua.overwatch.entity.DayUsage
import com.mua.overwatch.entity.PackageUsage
import com.mua.overwatch.repository.AppUsageRepository
import com.mua.overwatch.repository.DayUsageRepository

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val dayUsageRepository: DayUsageRepository = DayUsageRepository(application)
    val dayUsageList: LiveData<MutableList<DayUsage>>

    init {
        dayUsageList = dayUsageRepository.dayUsageList
    }

}