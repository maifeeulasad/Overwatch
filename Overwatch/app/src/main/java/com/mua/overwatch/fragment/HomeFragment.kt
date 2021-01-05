package com.mua.overwatch.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import java.util.*
import kotlin.collections.HashMap


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
        //viewModel.getAllInstalledApplication(requireActivity())

        val startCalendar = Calendar.getInstance()
        startCalendar.add(Calendar.HOUR,-10)
        val endCalendar = Calendar.getInstance()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //val res = viewModel.getUsageStatistics(requireContext())

            val result = viewModel.queryUsageStatistics(
                    requireContext(),
                    startCalendar.getTimeInMillis(),
                    endCalendar.getTimeInMillis())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                result!!.forEach { t, u ->
                    Log.d("d--mua--l",t)
                    Log.d("d--mua--l",u!!.seconds.toString())
                }
            }

        } else {
            TODO("VERSION.SDK_INT < LOLLIPOP")
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