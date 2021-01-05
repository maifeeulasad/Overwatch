package com.mua.overwatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.navigation.NavController
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        //startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
    }

    private fun init(){
        navController = Navigation.findNavController(this,R.id.frag_empty)
    }

}