package com.mua.overwatch.viewmodel

import android.app.Application
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.Log
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


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public fun queryUsageStatistics(context: Context, startTime: Long, endTime: Long): HashMap<String, AppUsage?>? {
        var currentEvent: UsageEvents.Event
        val allEvents: MutableList<UsageEvents.Event> = ArrayList()
        val map: HashMap<String, AppUsage?> = HashMap<String, AppUsage?>()
        val mUsageStatsManager = (context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager)
        // Here we query the events from startTime till endTime.
        val usageEvents = mUsageStatsManager.queryEvents(startTime, endTime)

        // go over all events.
        while (usageEvents.hasNextEvent()) {
            currentEvent = UsageEvents.Event()
            usageEvents.getNextEvent(currentEvent)
            val packageName = currentEvent.packageName
            if (currentEvent.eventType == UsageEvents.Event.ACTIVITY_RESUMED || currentEvent.eventType == UsageEvents.Event.ACTIVITY_PAUSED || currentEvent.eventType == UsageEvents.Event.ACTIVITY_STOPPED) {
                allEvents.add(currentEvent) // an extra event is found, add to all events list.
                // taking it into a collection to access by package name
                if (!map.containsKey(packageName)) {
                    map[packageName] = AppUsage()
                }
            }
        }

        // iterate through all events.
        for (i in 0 until allEvents.size - 1) {
            val event0 = allEvents[i]
            val event1 = allEvents[i + 1]

            //for launchCount of apps in time range
            if (event0.packageName != event1.packageName && event1.eventType == UsageEvents.Event.ACTIVITY_RESUMED) {
                // if true, E1 (launch event of an app) app launched
                Objects.requireNonNull(map[event1.packageName])!!.launchCount++
            }

            //for UsageTime of apps in time range
            if (event0.eventType == UsageEvents.Event.ACTIVITY_RESUMED &&
                    (event1.eventType == UsageEvents.Event.ACTIVITY_PAUSED || event1.eventType == UsageEvents.Event.ACTIVITY_STOPPED)
                    && event0.packageName == event1.packageName) {
                val diff = event1.timeStamp - event0.timeStamp
                Objects.requireNonNull(map[event0.packageName])!!.seconds += diff
            }
        }
        // and return the map.
        return map
    }
    

    fun getAllInstalledApplication(activity: FragmentActivity){
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
                Log.d("d--muax",appId)
                /*
                var tem = apps[appId]
                if(tem==null){
                    tem = AppUsage(appId,appName,Date(0),0, image)
                }else{
                    tem.seconds = 0
                    tem.appName = appName
                    tem.date = Date(0)
                    tem.icon = byteArrayOf()
                }
                apps[appId] = tem
                */
                //insert(AppUsage(appId,appName, Date(0) ,0,image))
            }
        }
    }

    init {
        appUsageList = appUsageRepository.appUsageList
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getUsageStatistics(context: Context): MutableMap<String, AppUsage> {

        val currentEvent:UsageEvents.Event
        val allEvents:MutableList<UsageEvents.Event> = ArrayList()
        val map:MutableMap<String, AppUsage> = HashMap();

        val currTime = System.currentTimeMillis();
        val startTime = currTime - 1000*3600*3; //querying past three hours

        val mUsageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        val usageEvents = mUsageStatsManager.queryEvents(startTime, currTime);

//capturing all events in a array to compare with next element

        while (usageEvents.hasNextEvent()) {
            val currentEvent = UsageEvents.Event();
            usageEvents.getNextEvent(currentEvent);
            if (currentEvent.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND ||
                    currentEvent.getEventType() == UsageEvents.Event.MOVE_TO_BACKGROUND) {
                allEvents.add(currentEvent);
                val key = currentEvent.getPackageName();
// taking it into a collection to access by package name
                if (map.get(key)==null)
                    map.put(key,AppUsage(key));
            }
        }


        allEvents.forEachIndexed({
            index, event ->
            val E0=allEvents.get(index);
            val E1=allEvents.get(index+1);

            if (!E0.getPackageName().equals(E1.getPackageName()) && E1.getEventType()==1){
                map.get(E1.getPackageName())!!.launchCount++;
            }

            if (E0.getEventType()==1 && E1.getEventType()==2
                    && E0.getClassName().equals(E1.getClassName())){
                val diff = E1.getTimeStamp()-E0.getTimeStamp();
                map.get(E0.getPackageName())!!.seconds+= diff;
            }
        })


        return map;

    }
    
    
    
}