package com.igdev.secondhand.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.ActivityMainBinding
import com.igdev.secondhand.lightStatusBar
import com.igdev.secondhand.setFullScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //Turn Off dark mode
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setFullScreen(window)
        lightStatusBar(window)

        val navController = findNavController(R.id.fragmentContainerView)
        binding.menuNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.menuNavigation.visibility = View.VISIBLE
                }
                R.id.notificationFragment -> {
                    binding.menuNavigation.visibility = View.VISIBLE
                }
                R.id.accountFragment -> {
                    binding.menuNavigation.visibility = View.VISIBLE
                }
                R.id.transactionFragment -> {
                    binding.menuNavigation.visibility = View.VISIBLE
                }

                else -> {
                    binding.menuNavigation.visibility = View.GONE
                }
            }
        }
    }

}