package com.travelapp.ui.fragment

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.travelapp.R
import com.travelapp.databinding.FragmentPlacesBinding
import com.travelapp.retrofit.PlacesModel
import com.travelapp.ui.PlaceDetailActivity
import com.travelapp.ui.adapter.PlacesAdaper
import com.travelapp.ui.viewmodel.MainActivityViewModel
import com.travelapp.utils.BindingAdapters.PLACES_KEY
import kotlinx.android.synthetic.main.fragment_places.*


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

        viewModel.userData.observe(activity!!, Observer {
            val places: List<PlacesModel.Row> =
                it!!.rows!!

            adapter = PlacesAdaper(places, this)
            manager = LinearLayoutManager(activity)
            binding.rvPlaces.adapter = adapter
            binding.rvPlaces.layoutManager = manager
            runAnimationAgain()
        })
        /* binding.rvPlaces.addOnScrollListener(object : RecyclerView.OnScrollListener() {
             override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                 super.onScrollStateChanged(recyclerView, newState)
             }
         })*/
    }

    override fun onProductItemClicked(places: PlacesModel.Row) {
        val intent = Intent(activity!!, PlaceDetailActivity::class.java)
        intent.putExtra(PLACES_KEY, places)
        // startActivity(intent)
        val options = ActivityOptions
            .makeSceneTransitionAnimation(activity!!, rvPlaces, "robot")
        // start the new activity
        //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity!!).toBundle())
        startActivity(intent, options.toBundle())
       /* startActivity(intent)
        activity!!.overridePendingTransition(0,0)*/



    }

    private fun runAnimationAgain() {
        val controller =
            AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_right_to_left)
        binding.rvPlaces.setLayoutAnimation(controller)
        binding.rvPlaces.adapter!!.notifyDataSetChanged()
        binding.rvPlaces.scheduleLayoutAnimation()
    }


}