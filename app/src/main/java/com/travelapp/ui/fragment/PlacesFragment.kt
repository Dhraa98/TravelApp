package com.travelapp.ui.fragment

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.travelapp.R
import com.travelapp.databinding.FragmentPlacesBinding
import com.travelapp.retrofit.PlacesModel
import com.travelapp.ui.PlaceDetailActivity
import com.travelapp.ui.adapter.PagingAdapter
import com.travelapp.ui.adapter.PlacesAdaper
import com.travelapp.ui.viewmodel.MainActivityViewModel
import com.travelapp.ui.viewmodel.PlacesFragmentViewModel
import com.travelapp.utils.BindingAdapters.ISLOADING
import com.travelapp.utils.BindingAdapters.PAGESIZE
import com.travelapp.utils.BindingAdapters.PLACES_KEY
import kotlinx.android.synthetic.main.fragment_places.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PlacesFragment : Fragment(), PlacesAdaper.ProductItemClickListener {
    private val TAG = "PlacesFragment"
    private lateinit var binding: FragmentPlacesBinding
    private lateinit var viewModel: PlacesFragmentViewModel
    private lateinit var adapter: PagingAdapter
    private lateinit var manager: LinearLayoutManager
    var isLoading = true
    var isLastPage = false

    // var places: MutableList<PlacesModel.Row> = mutableListOf()
    var placesListRow: ArrayList<PlacesModel.Row> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_places, container, false)

        viewModel = ViewModelProvider(this).get(PlacesFragmentViewModel::class.java)

        binding.lifecycleOwner = activity
        //  binding.viewmodel = viewModel
        manager = LinearLayoutManager(activity)
        initControls()
        return binding.root

    }

    private fun initControls() {
        initRecyclerView()


    }


    fun initRecyclerView() {

        adapter = PagingAdapter()


        binding.rvPlaces.adapter = adapter
        binding.rvPlaces.layoutManager = manager
        lifecycleScope.launch {
            viewModel.listData.collectLatest {
                adapter.submitData(it)
            }
        }
        adapter.addLoadStateListener {loadState ->
            if (loadState.refresh == LoadState.Loading){
                binding.progress.visibility = View.VISIBLE
            }
            else{
                binding.progress.visibility = View.GONE

                // getting the error
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }

            }

        }
    }

    override fun onProductItemClicked(places: PlacesModel.Row) {
        val intent = Intent(activity!!, PlaceDetailActivity::class.java)
        intent.putExtra(PLACES_KEY, places)
        val options = ActivityOptions
            .makeSceneTransitionAnimation(activity!!, rvPlaces, "robot")

        startActivity(intent, options.toBundle())


    }

    private fun runAnimationAgain() {
        val controller =
            AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_right_to_left)
        binding.rvPlaces.setLayoutAnimation(controller)
        binding.rvPlaces.adapter!!.notifyDataSetChanged()
        binding.rvPlaces.scheduleLayoutAnimation()
    }


}