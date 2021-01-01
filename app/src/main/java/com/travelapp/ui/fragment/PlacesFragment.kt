package com.travelapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.travelapp.R
import com.travelapp.databinding.FragmentPlacesBinding
import com.travelapp.retrofit.PlacesModel
import com.travelapp.ui.adapter.PlacesAdaper
import com.travelapp.ui.viewmodel.MainActivityViewModel


class PlacesFragment : Fragment(), PlacesAdaper.ProductItemClickListener {
    private lateinit var binding: FragmentPlacesBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: PlacesAdaper
    private lateinit var manager: RecyclerView.LayoutManager
    var placesListRow: List<PlacesModel> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_places, container, false)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.lifecycleOwner = activity
        binding.viewmodel = viewModel
        initControls()
        return binding.root

    }

    private fun initControls() {

        viewModel.userData.observe(requireActivity(), Observer {
            val places: List<PlacesModel.Row> =
                it?.rows!!

            adapter = PlacesAdaper(places, this)
            manager = LinearLayoutManager(activity)
            binding.rvPlaces.adapter = adapter
            binding.rvPlaces.layoutManager = manager
            runAnimationAgain()
        })

    }

    override fun onProductItemClicked(places: PlacesModel.Row) {

        val action = PlacesFragmentDirections.actionPlacesFragmentToPlaceDetailActivity(places)
        val navController =
            Navigation.findNavController(requireActivity(), R.id.my_nav_host_fragment)
        navController.navigate(action)


    }

    private fun runAnimationAgain() {
        val controller =
            AnimationUtils.loadLayoutAnimation(
                requireActivity(),
                R.anim.layout_animation_right_to_left
            )
        binding.rvPlaces.setLayoutAnimation(controller)
        binding.rvPlaces.adapter?.notifyDataSetChanged()
        binding.rvPlaces.scheduleLayoutAnimation()
    }


}