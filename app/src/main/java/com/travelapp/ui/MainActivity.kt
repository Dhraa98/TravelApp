package com.travelapp.ui

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.travelapp.R
import com.travelapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration : AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initControls()
    }

    private fun initControls() {
        binding.lifecycleOwner = this
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController


        NavigationUI.setupWithNavController(binding.navigation,navController)


        appBarConfiguration = AppBarConfiguration(navController.graph)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id==R.id.page_1){
                binding.navigation.visibility=View.VISIBLE
            }else if(destination.id==R.id.page_2){
                binding.navigation.visibility=View.VISIBLE
            }else{
                binding.navigation.visibility=View.GONE
            }
        }
        /*var placesFragment: PlacesFragment = PlacesFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.frm_contain, placesFragment)
            .commit()

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    var placesFragment: PlacesFragment = PlacesFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frm_contain, placesFragment)
                        .commit()
                    // Respond to navigation item 1 click
                    true
                }
                R.id.page_2 -> {
                    var favouritesFragment: FavouritesFragment = FavouritesFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frm_contain, favouritesFragment)
                        .commit()
                    // Respond to navigation item 2 click
                    true
                }
                else -> false
            }
        }*/
    }
}