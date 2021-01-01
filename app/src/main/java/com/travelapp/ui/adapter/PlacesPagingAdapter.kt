package com.travelapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.travelapp.databinding.ItemAdapterListBinding
import com.travelapp.retrofit.PlacesModel

class PlacesPagingAdapter : PagingDataAdapter<PlacesModel.Row, PlacesPagingAdapter.ViewHolder>(DataDifferntiator) {
    class ViewHolder(var binding: ItemAdapterListBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onBindViewHolder(holder: PlacesPagingAdapter.ViewHolder, position: Int) {


        getItem(position).let {


                holder.binding.tvPrice.text= getItem(position)?.minPrice
                holder.binding.ivLocation.load(getItem(position)?.images?.get(0)?.imagePath) {
                    crossfade(true)

                }
                holder.binding.tvLocationName.text=getItem(position)?.listingName
                holder.binding.tvRating.text=getItem(position)?.avgRating


        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesPagingAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAdapterListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    object DataDifferntiator : DiffUtil.ItemCallback<PlacesModel.Row>() {


        override fun areItemsTheSame(oldItem: PlacesModel.Row, newItem: PlacesModel.Row): Boolean {
            return oldItem.listingName==newItem.listingName
        }

        override fun areContentsTheSame(
            oldItem: PlacesModel.Row,
            newItem: PlacesModel.Row
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }
}