package com.travelapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.RecyclerView
import com.travelapp.R

import com.travelapp.databinding.FragmentFavouritesBinding


class FavouritesFragment : Fragment() {
    private val TAG = "FavouritesFragment"

    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var binding: FragmentFavouritesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourites, container, false)
        binding.lifecycleOwner = this

        return binding.root

    }




}


