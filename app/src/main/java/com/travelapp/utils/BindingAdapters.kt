package com.travelapp.utils

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import coil.load
import com.travelapp.database.TodoEntity
import com.travelapp.retrofit.PlacesModel


object BindingAdapters {

    var PLACES_KEY : String=""
    var PAGESIZE : Int=10
    var ISLOADING : Boolean = false
     var dataList: MutableList<TodoEntity> = mutableListOf()
    val places: List<PlacesModel.Row> = listOf()
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: ImageView, url: String?) {

        imageView.load(url) {
            crossfade(true)

        }
    }




}