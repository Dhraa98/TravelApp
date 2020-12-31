package com.travelapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.travelapp.databinding.ItemAdapterListBinding
import com.travelapp.retrofit.PlacesModel

class UserPagingAdapter : PagingDataAdapter<PlacesModel.Row, UserPagingAdapter.ViewHolder>(DataDifferntiator) {
    class ViewHolder(var binding: ItemAdapterListBinding) : RecyclerView.ViewHolder(binding.root) /*{
        fun bind(placesList: PlacesModel.Row) {

            binding.placesModel = placesList
           // binding.itemClick = listener
           // binding.viewmodel = viewModel
            binding.executePendingBindings()


        }
    }*/

    override fun onBindViewHolder(holder: UserPagingAdapter.ViewHolder, position: Int) {


        getItem(position).let {


                holder.binding.tvPrice.text= getItem(position)!!.minPrice
                holder.binding.ivLocation.load(getItem(position)!!.images!![0].imagePath) {
                    crossfade(true)

                }
                holder.binding.tvLocationName.text=getItem(position)!!.listingName
                holder.binding.tvRating.text=getItem(position)!!.avgRating


        }

       /* val places : PlacesModel.Row = getItem(0)!!.rows!![position]!!
        holder.bind(places)*/


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPagingAdapter.ViewHolder {
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