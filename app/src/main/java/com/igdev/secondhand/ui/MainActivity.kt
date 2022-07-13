package com.igdev.secondhand.ui

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
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

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }

        //setFullScreen(window)
        //lightStatusBar(window)





        /*val navController = findNavController(R.id.fragmentContainerView)
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
        binding.menuNavigation.selectedItemId = if (activePage == 0){
            R.id.homeFragment
        }else{
            activePage
        }*/
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }
    //1
    private var posTop = 0
    private var posBottom = 0


}