package com.mua.overwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init(){
        navController = Navigation.findNavController(this,R.id.frag_empty)

        //navController = NavHostController(this)
        //navController.navigate(R.id.emptyFragment)
    }

}