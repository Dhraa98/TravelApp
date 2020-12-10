package com.travelapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil

import com.travelapp.R
import com.travelapp.database.TodoEntity
import com.travelapp.database.TodoRoomDatabase
import com.travelapp.databinding.ActivityPlaceDetailBinding
import com.travelapp.retrofit.PlacesModel
import com.travelapp.utils.BindingAdapters.PLACES_KEY
import com.travelapp.utils.BindingAdapters.dataList
import kotlinx.android.synthetic.main.activity_place_detail.*

class PlaceDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaceDetailBinding
    var hs: HashSet<TodoEntity> = HashSet()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_place_detail)
        initControls()
    }

    private fun initControls() {
        binding.lifecycleOwner = this
        val productData = intent.getSerializableExtra(PLACES_KEY)

        binding.placesData = productData as PlacesModel.Row?
        ivFav.setOnClickListener {

            insertAll()
        }
        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun insertAll() {
        var todoEntity = TodoEntity()
        val path = binding.placesData!!.images!!.get(0).imagePath
        //  todoEntity.Id = productData.placesData!!.listingId
        todoEntity.avgRating = binding.tvAvgRating.text.toString()
        todoEntity.listingName = binding.ivLocatinText.text.toString()
        todoEntity.minPrice = binding.tvMinPrice.text.toString()
        todoEntity.imagePath = path!!

        TodoRoomDatabase.getDatabase(this).todoDao().getAll().forEach()
        {
            dataList.addAll(listOf(it))
            hs.add(it)
            Log.i("Fetch Records", "Id:  : ${it.Id}")
            Log.i("Fetch Records", "Name:  : ${it.listingName}")

        }
        if (dataList.size == 0) {
            TodoRoomDatabase.getDatabase(this).todoDao().insertAll(todoEntity)
            ivFav.setImageResource(R.drawable.ic_baseline_favorite_24)
            ivFav.setColorFilter(
                ContextCompat.getColor(this, R.color.my_app_error_color),
                android.graphics.PorterDuff.Mode.SRC_IN
            );
        } else {
            loop@ for (i in dataList.indices) {
                if (dataList[i].listingName.contains(binding.ivLocatinText.text.toString())) {
                    Toast.makeText(this, "This place is already in favourite", Toast.LENGTH_LONG)
                        .show()
                    break@loop
                } else {
                    TodoRoomDatabase.getDatabase(this).todoDao().insertAll(todoEntity)
                    ivFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                    ivFav.setColorFilter(
                        ContextCompat.getColor(this, R.color.my_app_error_color),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    );



                    break@loop
                }
            }


        }


    }
}