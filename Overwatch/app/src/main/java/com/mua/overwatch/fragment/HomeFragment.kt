package com.mua.overwatch.fragment

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mua.overwatch.R
import com.mua.overwatch.adapter.AppUsageListAdapter
import com.mua.overwatch.databinding.FragmentHomeBinding
import com.mua.overwatch.entity.AppUsage
import com.mua.overwatch.viewmodel.HomeViewModel
import java.io.ByteArrayOutputStream
import java.sql.Date
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var appUsageListAdapter: AppUsageListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater, R.layout.fragment_home, container, false
        ) as FragmentHomeBinding
        val view: View = mBinding.root
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        mBinding.home = viewModel
        mBinding.lifecycleOwner = this
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getAllInstalledApplication()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getUsage()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getUsage(){
        val manager: UsageStatsManager
                = context?.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        val timeNow = System.currentTimeMillis()
        val cal: Calendar = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)

        val range: LongArray = longArrayOf(cal.getTimeInMillis(), timeNow)
        val events = manager.queryEvents(range[0],range[1])
        val event: UsageEvents.Event = UsageEvents.Event()

        while (events.hasNextEvent()){
            events.getNextEvent(event)
            if(event.eventType==UsageEvents.Event.ACTIVITY_RESUMED){
                Log.d("d--mua",event.packageName+" "+Date(event.timeStamp).toGMTString()+" ")
                val diff = System.currentTimeMillis().toInt() - event.getTimeStamp()
                Log.d("d--mua",diff.toString())
            }
        }
    }

    private fun getAllInstalledApplication(){
        val packageManager = requireActivity().packageManager;
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

                viewModel.insert(AppUsage(appId,appName, Date(0),0,image))
            }
        }
    }

    private fun init(){
        appUsageListAdapter = AppUsageListAdapter(context)
        val appUsageRecyclerView: RecyclerView = mBinding.rvUsage
        appUsageRecyclerView.adapter = appUsageListAdapter
        appUsageRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.appUsageList.observe(mBinding.lifecycleOwner!!, Observer {
            Toast.makeText(requireContext(),"List has been Updated : " + it.size,Toast.LENGTH_LONG).show()
            appUsageListAdapter.setAppUsages(it)
        })
    }

}