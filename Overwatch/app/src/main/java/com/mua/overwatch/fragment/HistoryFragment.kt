package com.mua.overwatch.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.mua.overwatch.R
import com.mua.overwatch.adapter.AppUsageListAdapter
import com.mua.overwatch.adapter.HistoryListAdapter
import com.mua.overwatch.databinding.FragmentHistoryBinding
import com.mua.overwatch.databinding.FragmentHomeBinding
import com.mua.overwatch.viewmodel.HistoryViewModel
import com.mua.overwatch.viewmodel.HomeViewModel


class HistoryFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var viewModel: HistoryViewModel
    private lateinit var mBinding: FragmentHistoryBinding
    private lateinit var appUsageListAdapter: HistoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater, R.layout.fragment_history, container, false
        ) as FragmentHistoryBinding
        val view: View = mBinding.root
        viewModel = ViewModelProvider(requireActivity()).get(HistoryViewModel::class.java)
        mBinding.history = viewModel
        mBinding.lifecycleOwner = this
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }


    private fun init() {
        appUsageListAdapter = HistoryListAdapter()
        val appUsageRecyclerView: RecyclerView = mBinding.rvDayUsage
        appUsageRecyclerView.adapter = appUsageListAdapter
        appUsageRecyclerView.layoutManager = LinearLayoutManager(requireActivity())


        mBinding.dlHistory.open()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_home_history){

        }else if(item.itemId==R.id.menu_home_info){

        }else if(item.itemId==R.id.menu_home_setting){

        }
        return false
    }

}