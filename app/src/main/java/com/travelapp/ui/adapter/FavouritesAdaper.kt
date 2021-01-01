package com.travelapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.travelapp.database.TodoEntity
import com.travelapp.databinding.ItemAdapterListBinding
import com.travelapp.databinding.ItemDbListBinding
import com.travelapp.retrofit.PlacesModel

class FavouritesAdaper(
    var dataList: MutableList<TodoEntity>
) :
    RecyclerView.Adapter<FavouritesAdaper.ViewHolder>() {
    class ViewHolder(var binding: ItemDbListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataList: TodoEntity) {

            binding.placesModel1 = dataList
            binding.executePendingBindings()


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesAdaper.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDbListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {

            return dataList.size


    }

    override fun onBindViewHolder(holder: FavouritesAdaper.ViewHolder, position: Int) =

            holder.bind(dataList[position])




}