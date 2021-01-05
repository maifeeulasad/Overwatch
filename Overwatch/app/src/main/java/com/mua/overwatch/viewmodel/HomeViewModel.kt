package com.mua.overwatch.viewmodel

import android.app.Application
import android.app.usage.UsageEvents
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mua.overwatch.entity.AppUsage
import com.mua.overwatch.repository.AppUsageRepository
import java.io.ByteArrayOutputStream
import java.sql.Date
import java.util.*
import kotlin.collections.HashMap

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val appUsageRepository: AppUsageRepository = AppUsageRepository(application)
    val appUsageList: LiveData<List<AppUsage>>
    val apps: MutableMap<String,AppUsage> = HashMap()

    private fun insert(appUsage: AppUsage?) {
        appUsageRepository.insert(appUsage)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun mapToDatabase(){
        apps.forEach { t, u ->
            insert(u)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getUsage(context: Context){
        val manager: UsageStatsManager
                = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        val cal: Calendar = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)

        val stats: List<UsageStats> = manager.queryUsageStats(
                UsageStatsManager,
                cal.timeInMillis,
                System.currentTimeMillis())

        for(usageStat in stats){
            val tem = apps[usageStat.packageName]
            Log.d("d--muay",usageStat.packageName)
            tem?.seconds = usageStat.totalTimeInForeground
            /*
            if(tem==null){
                tem = AppUsage(usageStat.packageName,"",Date(0),0, byteArrayOf())
            }else{
                tem.seconds = usageStat.totalTimeInForeground
            }
             */
            apps.put(usageStat.packageName,tem!!)
        }

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
}