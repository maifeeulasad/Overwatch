package com.mua.overwatch.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mua.overwatch.R
import com.mua.overwatch.databinding.FragmentSplashBinding
import com.mua.overwatch.viewmodel.EmptyViewModel

class SplashFragment : Fragment() {
    
    private lateinit var viewModel: EmptyViewModel
    private lateinit var mBinding: FragmentSplashBinding
    private val delay = 2L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater, R.layout.fragment_splash, container, false
        ) as FragmentSplashBinding
        val view: View = mBinding.root
        viewModel = ViewModelProvider(requireActivity()).get(EmptyViewModel::class.java)
        mBinding.empty = viewModel
        mBinding.lifecycleOwner = this
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        Handler(Looper.getMainLooper()).postDelayed({
            startHome()
        }, delay*1000)
    }

    private fun startHome(){
        val navController = Navigation.findNavController(requireActivity(),R.id.frag_empty)
        navController.navigate(R.id.nav_frag_home)
    }

}