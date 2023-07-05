package com.example.madproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.madproject.databinding.ActivitySrvicesSBinding

class Srvices_S : AppCompatActivity() {

    private lateinit var binding: ActivitySrvicesSBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)



        binding =ActivitySrvicesSBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.fragmentContainerView)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,R.id.addFragment))

       // setupActionBarWithNavController(navController,appBarConfiguration)

    }

}