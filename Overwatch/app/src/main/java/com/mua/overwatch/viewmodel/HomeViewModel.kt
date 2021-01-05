package com.mua.overwatch.viewmodel

import android.app.Application
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mua.overwatch.entity.AppUsage
import com.mua.overwatch.repository.AppUsageRepository
import java.io.ByteArrayOutputStream
import java.sql.Date
import java.util.*
import kotlin.collections.ArrayList


class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val appUsageRepository: AppUsageRepository = AppUsageRepository(application)
    val appUsageList: LiveData<List<AppUsage>>
    val apps: MutableMap<String,AppUsage> = HashMap()

    private fun insert(appUsage: AppUsage?) {
        appUsageRepository.insert(appUsage)
    }


    private fun storeInDatabase(){
        for(entry in apps){
            insert(entry.value)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun queryUsageStatistics(context: Context, startTime: Long, endTime: Long) {
        val events: MutableList<UsageEvents.Event> = ArrayList()
        val usageManager = context.getSystemService(Context.USAGE_STATS_SERVICE)
                as UsageStatsManager
        val usageEvents = usageManager.queryEvents(startTime, endTime)

        while (usageEvents.hasNextEvent()) {
            val currentEvent = UsageEvents.Event()
            usageEvents.getNextEvent(currentEvent)
            if (currentEvent.eventType == UsageEvents.Event.ACTIVITY_RESUMED
                || currentEvent.eventType == UsageEvents.Event.ACTIVITY_PAUSED
                || currentEvent.eventType == UsageEvents.Event.ACTIVITY_STOPPED) {
                events.add(currentEvent)
            }
        }

        for (i in 0 until events.size - 1) {
            val e0 = events[i]
            val e1 = events[i + 1]

            if (e0.eventType == UsageEvents.Event.ACTIVITY_RESUMED
                && (e1.eventType == UsageEvents.Event.ACTIVITY_PAUSED
                        || e1.eventType == UsageEvents.Event.ACTIVITY_STOPPED)
                && e0.packageName == e1.packageName) {
                val diff = e1.timeStamp - e0.timeStamp
                if(apps.containsKey(e0.packageName)){
                    Objects.requireNonNull(apps[e0.packageName])!!.duration += diff
                }
            }
        }
    }
    

    private fun getAllInstalledApplication(activity: FragmentActivity){
        val packageManager = activity.packageManager;
        val list = packageManager!!.getInstalledPackages(0)
        for (i in list.indices) {
            val packageInfo = list[i]
            if (packageInfo!!.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                val appName = packageInfo.applicationInfo.loadLabel(packageManager).toString()
                val appDrawable = packageInfo.applicationInfo.loadIcon(packageManager)
                val appId = packageInfo.packageName

                val bitmap = (appDrawable as BitmapDrawable).getBitmap()
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val image = stream.toByteArray()


                apps[appId] = AppUsage(appId,appName,Date(0),0,image)
            }
        }
    }

    fun getLast24Hours(context: Context,activity: FragmentActivity){
        getAllInstalledApplication(activity)

        val startCalendar = Calendar.getInstance()
        startCalendar.set(Calendar.SECOND,0)
        startCalendar.set(Calendar.MINUTE,0)
        startCalendar.set(Calendar.HOUR,0)
        val endCalendar = Calendar.getInstance()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            queryUsageStatistics(
                context,
                startCalendar.timeInMillis,
                endCalendar.timeInMillis
            )
        } else {
            Toast
                .makeText(context,
                    "Min requirement Lollipop",
                    Toast.LENGTH_LONG)
                .show()
        }
        storeInDatabase()
    }

    init {
        appUsageList = appUsageRepository.appUsageList
    }
    
}