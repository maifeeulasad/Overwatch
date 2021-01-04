package com.mua.overwatch.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.mua.overwatch.R
import com.mua.overwatch.databinding.FragmentEmptyBinding
import com.mua.overwatch.viewmodel.EmptyViewModel


class EmptyFragment : Fragment() {

    private lateinit var viewModel: EmptyViewModel
    private lateinit var mBinding: FragmentEmptyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater, R.layout.fragment_empty, container, false
        ) as FragmentEmptyBinding
        val view: View = mBinding.root
        viewModel = ViewModelProvider(requireActivity()).get(EmptyViewModel::class.java)
        mBinding.empty = viewModel
        mBinding.lifecycleOwner = this
        return view

    }

}