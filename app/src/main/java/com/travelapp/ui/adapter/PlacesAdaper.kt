package com.travelapp.ui.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.travelapp.database.TodoEntity
import com.travelapp.databinding.ItemAdapterListBinding

import com.travelapp.retrofit.PlacesModel
import com.travelapp.ui.viewmodel.MainActivityViewModel
import com.travelapp.utils.BindingAdapters

class PlacesAdaper(
    val places: List<PlacesModel.Row>, val viewModel: MainActivityViewModel,
    private val mListener: ProductItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var img: ImageView

    class Item(var binding: ItemAdapterListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placesList: PlacesModel.Row,viewModel: MainActivityViewModel, listener: ProductItemClickListener) {

            binding.placesModel = placesList
            binding.itemClick = listener
            binding.viewmodel = viewModel
            binding.executePendingBindings()


        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAdapterListBinding.inflate(inflater, parent, false)
        return Item(binding)

    }

    override fun getItemCount(): Int {

        return places.size


    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Item).bind(places[position],viewModel, mListener)
        if(position.equals(places.size-1)){
            holder.binding.progress.visibility = View.VISIBLE
        }else{
            holder.binding.progress.visibility = View.GONE
        }


    }


// holder.bind(places[position], mListener)

    interface ProductItemClickListener {
        fun onProductItemClicked(places: PlacesModel.Row)
    }



}