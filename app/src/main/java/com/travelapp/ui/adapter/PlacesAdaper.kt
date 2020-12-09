package com.travelapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travelapp.databinding.ItemAdapterListBinding
import com.travelapp.retrofit.PlacesModel

class PlacesAdaper(val places: List<PlacesModel.Row>) :
    RecyclerView.Adapter<PlacesAdaper.ViewHolder>() {
    class ViewHolder(var binding: ItemAdapterListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placesList: PlacesModel.Row) {

            binding.placesModel = placesList
            binding.executePendingBindings()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesAdaper.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAdapterListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return places.size
    }

    override fun onBindViewHolder(holder: PlacesAdaper.ViewHolder, position: Int) =
        holder.bind(places[position])

}