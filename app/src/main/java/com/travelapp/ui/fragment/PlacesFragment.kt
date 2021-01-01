package com.travelapp.ui.fragment

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.travelapp.R
import com.travelapp.databinding.FragmentPlacesBinding
import com.travelapp.retrofit.PlacesModel
import com.travelapp.ui.adapter.FooterAdapter
import com.travelapp.ui.adapter.PlacesPagingAdapter
import com.travelapp.ui.viewmodel.PlacesFragmentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PlacesFragment : Fragment() {
    private val TAG = "PlacesFragment"
    private lateinit var binding: FragmentPlacesBinding
    private lateinit var viewModel: PlacesFragmentViewModel
    private lateinit var adapter: PlacesPagingAdapter
    private lateinit var manager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_places, container, false)

        viewModel = ViewModelProvider(this).get(PlacesFragmentViewModel::class.java)

        binding.lifecycleOwner = activity
        manager = LinearLayoutManager(activity)
        initControls()
        return binding.root

    }

    private fun initControls() {
        initRecyclerView()


    }


    fun initRecyclerView() {

        adapter = PlacesPagingAdapter()


        binding.rvPlaces.adapter = adapter
        binding.rvPlaces.layoutManager = manager
        lifecycleScope.launch {
            viewModel.listData.collectLatest {

                adapter.submitData(it as PagingData<PlacesModel.Row>)
            }
        }


        binding.rvPlaces.adapter = adapter.withLoadStateFooter(
            FooterAdapter()
        )

    }





}