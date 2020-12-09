package com.travelapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStores.of
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.travelapp.R
import com.travelapp.databinding.FragmentPlacesBinding
import com.travelapp.retrofit.PlacesModel
import com.travelapp.ui.adapter.PlacesAdaper
import com.travelapp.ui.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_places.*
import java.util.EnumSet.of

class PlacesFragment : Fragment() {
    private lateinit var binding: FragmentPlacesBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: PlacesAdaper
    private lateinit var manager: RecyclerView.LayoutManager
    var placesList: List<PlacesModel> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_places, container, false)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.lifecycleOwner = activity
        binding.viewmodel = viewModel
        return binding.root
        initControls()
    }

    private fun initControls() {

        viewModel.userData.observe(activity!!, Observer {
            val places: List<PlacesModel.Row> =
                it!!.rows!!

            adapter = PlacesAdaper(places)
            manager = LinearLayoutManager(activity)
            rvPlaces.adapter = adapter
            rvPlaces.layoutManager = manager
        })
    }
}