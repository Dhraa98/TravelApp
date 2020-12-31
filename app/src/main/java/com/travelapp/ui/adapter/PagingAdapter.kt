package com.travelapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.travelapp.databinding.ItemAdapterListBinding
import com.travelapp.retrofit.PlacesModel
import com.travelapp.ui.viewmodel.MainActivityViewModel

class PagingAdapter : PagingDataAdapter<PlacesModel, PagingAdapter.ViewHolder>(DataDifferntiator) {
    class ViewHolder(var binding: ItemAdapterListBinding) : RecyclerView.ViewHolder(binding.root) /*{
        fun bind(placesList: PlacesModel.Row) {

            binding.placesModel = placesList
           // binding.itemClick = listener
           // binding.viewmodel = viewModel
            binding.executePendingBindings()


        }
    }*/

    override fun onBindViewHolder(holder: PagingAdapter.ViewHolder, position: Int) {
       /* holder.binding.ivLocation.load(getItem(0)!!.rows!![position]!!.images!![0].imagePath) {
            crossfade(true)

        }
        holder.binding.tvLocationName.text=getItem(0)!!.rows!![position]!!.listingName
        holder.binding.tvRating.text=getItem(0)!!.rows!![position]!!.avgRating
        holder.binding.tvPrice.text=getItem(0)!!.rows!![position]!!.minPrice*/
        getItem(position).let {

            for(i in it!!.rows!!.indices){
                holder.binding.tvPrice.text= it.rows!![i].minPrice
                holder.binding.ivLocation.load(it.rows!![i].images!![0].imagePath) {
                    crossfade(true)

                }
                holder.binding.tvLocationName.text=it.rows!![i].listingName
                holder.binding.tvRating.text=it.rows!![i].avgRating
            }

        }

       /* val places : PlacesModel.Row = getItem(0)!!.rows!![position]!!
        holder.bind(places)*/


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAdapterListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    object DataDifferntiator : DiffUtil.ItemCallback<PlacesModel>() {
        override fun areItemsTheSame(oldItem: PlacesModel, newItem: PlacesModel): Boolean {
            return oldItem.rows!![0].listingName == newItem.rows!![0].listingName
        }

        override fun areContentsTheSame(oldItem: PlacesModel, newItem: PlacesModel): Boolean {
            return oldItem.equals(newItem)
        }

    }
}