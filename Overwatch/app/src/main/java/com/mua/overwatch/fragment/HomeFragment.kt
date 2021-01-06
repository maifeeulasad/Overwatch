package com.mua.overwatch.fragment

import android.os.Bundle
import android.view.LayoutInflater
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
import com.mua.overwatch.R
import com.mua.overwatch.adapter.AppUsageListAdapter
import com.mua.overwatch.databinding.FragmentHomeBinding
import com.mua.overwatch.viewmodel.HomeViewModel


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
    }


    private fun init() {
        appUsageListAdapter = AppUsageListAdapter(context)
        val appUsageRecyclerView: RecyclerView = mBinding.rvUsage
        appUsageRecyclerView.adapter = appUsageListAdapter
        appUsageRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        //(activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        //requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        mBinding.dlHome.open()


        val items = arrayOf("Name", "Usage")
        val adapter: ArrayAdapter<*> =
            ArrayAdapter<Any?>(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                items
            )
        mBinding.spSort.adapter = adapter

        mBinding.spSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                appUsageListAdapter.sortBy(items[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        mBinding.ibSortDirection.setOnClickListener {
            appUsageListAdapter.reverseSortDirection()
        }

        viewModel.getLast24Hours(requireContext(), requireActivity())

        viewModel.packageUsageList.observe(mBinding.lifecycleOwner!!, Observer {
            appUsageListAdapter.setAppUsages(it)
        })
    }

}