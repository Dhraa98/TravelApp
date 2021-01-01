package com.travelapp.ui.fragment

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.travelapp.R
import com.travelapp.databinding.FragmentPlacesBinding
import com.travelapp.retrofit.PlacesModel
import com.travelapp.ui.adapter.PlacesAdaper
import com.travelapp.ui.viewmodel.MainActivityViewModel
import com.travelapp.utils.BindingAdapters.PAGESIZE
import com.travelapp.utils.BindingAdapters.PLACES_KEY
import kotlinx.android.synthetic.main.fragment_places.*


class PlacesFragment : Fragment(), PlacesAdaper.ProductItemClickListener {
    private val TAG = "PlacesFragment"
    private lateinit var binding: FragmentPlacesBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: PlacesAdaper
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
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        binding.lifecycleOwner = activity
        binding.viewmodel = viewModel
        manager = LinearLayoutManager(activity)
        initControls()
        return binding.root

    }

    private fun initControls() {

        addObserver()
        viewModel.userData()


        binding.rvPlaces.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = manager.childCount
                val totalItemCount = manager.itemCount
                val firstVisibleItemPosition = manager.findFirstVisibleItemPosition()
                if (!isLastPage) {
                    if (isLoading) {
                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {

                            isLoading = false
                            viewModel.pageNumber++
                            viewModel.userData()
                            Log.e(TAG, "MATCH_PAGE_NUMBER: ${viewModel.pageNumber}")


                        }
                    }
                }
            }


        })


    }

    private fun addObserver() {

        activity?.let { viewModel.data.observe(it, mObserver) }
    }

    val mObserver = Observer<Any> {

        isLoading = true

        for (i in (it!! as PlacesModel).rows!!.indices) {
            placesListRow.add((it as PlacesModel).rows!![i])
        }

        if (viewModel.pageNumber == 1) {
            initRecyclerView()
        } else {
            adapter.notifyDataSetChanged()
        }

        if ((it as PlacesModel).rows?.size!! < PAGESIZE) {
            isLastPage = true
        }
    }

    fun initRecyclerView() {

        adapter = PlacesAdaper(placesListRow,viewModel, this)

        binding.rvPlaces.adapter = adapter
        binding.rvPlaces.layoutManager = manager
    }

    override fun onProductItemClicked(places: PlacesModel.Row) {


    }


}