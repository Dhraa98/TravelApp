package com.travelapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.travelapp.R
import com.travelapp.databinding.ActivityMainBinding
import com.travelapp.ui.fragment.FavouritesFragment
import com.travelapp.ui.fragment.PlacesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initControls()
    }

    private fun initControls() {
        binding.lifecycleOwner = this
        var placesFragment: PlacesFragment = PlacesFragment()
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
        }
    }
}