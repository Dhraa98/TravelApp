package com.travelapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.travelapp.R
import com.travelapp.database.TodoRoomDatabase
import com.travelapp.databinding.FragmentFavouritesBinding
import com.travelapp.retrofit.PlacesModel
import com.travelapp.ui.adapter.FavouritesAdaper
import com.travelapp.ui.adapter.PlacesAdaper
import com.travelapp.utils.BindingAdapters.dataList
import kotlinx.android.synthetic.main.fragment_favourites.*
import kotlinx.android.synthetic.main.fragment_places.*

class FavouritesFragment : Fragment() {
    private val TAG = "FavouritesFragment"
    private lateinit var adapter: FavouritesAdaper
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var binding: FragmentFavouritesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourites, container, false)
        binding.lifecycleOwner = this
        initControls()
        return binding.root

    }

    private fun initControls() {
        dataList.clear()
        activity?.let {
            TodoRoomDatabase.getDatabase(it).todoDao().getAll().forEach()
            {
                dataList.addAll(listOf(it))
                Log.i("Fetch Records", "Id:  : ${it.Id}")
                Log.i("Fetch Records", "Name:  : ${it.listingName}")
            }
        }
        binding.progress.visibility = View.GONE
        adapter = FavouritesAdaper(dataList)
        manager = LinearLayoutManager(activity)
        binding.rvDbList.adapter = adapter
        binding.rvDbList.layoutManager = manager
        adapter.notifyDataSetChanged()
    }


}


