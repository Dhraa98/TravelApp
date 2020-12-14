package com.travelapp.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.util.Log
import android.view.Window
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
    private val TAG = "PlaceDetailActivity"
    private lateinit var binding: ActivityPlaceDetailBinding
    var dataList11: MutableList<TodoEntity> = mutableListOf()
    private var favouriteItem: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setAllowEnterTransitionOverlap(false);
        getWindow().setAllowReturnTransitionOverlap(false);


        //getWindow().setSharedElementEnterTransition(enterTransition());
        //getWindow().setSharedElementReturnTransition(returnTransition());
        getWindow().setSharedElementEnterTransition(null);
        getWindow().setSharedElementReturnTransition(null);
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_place_detail)
        initControls()
    }

    private fun initControls() {
        binding.lifecycleOwner = this
        val productData = intent.getSerializableExtra(PLACES_KEY)


        binding.placesData = productData as PlacesModel.Row?

        dataList.clear()
        TodoRoomDatabase.getDatabase(this).todoDao().getAll().forEach()

        {
            dataList.addAll(listOf(it))


        }
        slideUp()

        /*if (favouriteItem) {
            binding.placesData!!.isFavourite = false
            ivFav.setImageResource(R.drawable.ic_baseline_favorite_24)
            ivFav.setColorFilter(
                ContextCompat.getColor(this, R.color.my_app_error_color),
                android.graphics.PorterDuff.Mode.SRC_IN
            );
        }*/
        for (i in dataList.indices) {
            Log.e(TAG, "initControls: " + dataList[i].listingName)
            if (binding.placesData!!.listingName.equals(dataList[i].listingName)) {
                favouriteItem = true
                ivFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                ivFav.setColorFilter(
                    ContextCompat.getColor(this, R.color.my_app_error_color),
                    android.graphics.PorterDuff.Mode.SRC_IN
                );
            }
        }
        binding.ivFav.setOnClickListener {
            if (favouriteItem) {
                deleteAll()
            } else {
                insertAll()
            }

        }
        binding.ivLocation.setOnClickListener {
            val intent = Intent(this, DemoActivity::class.java)
            // startActivity(intent)
            val options = ActivityOptions
                .makeSceneTransitionAnimation(this, ivBack, "robot")
            // start the new activity
            //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity!!).toBundle())
            startActivity(intent, options.toBundle())
            ;
        }
        binding.ivBack.setOnClickListener {
            ///   binding.imgAnimator.clearAnimation();
            onBackPressed()
        }
        /* binding.imgAnimator.setOnClickListener {
             binding.imgAnimator.clearAnimation();
             binding.imgAnimator.visibility = View.GONE
             binding.imgAnimator.setBackgroundColor(resources.getColor(R.color.transparent))
             val slideUpAnimation = AnimationUtils.loadAnimation(
                 getApplicationContext(),
                 R.anim.slide_up_animation
             );

             binding.lnAnimator.startAnimation(slideUpAnimation)

             binding.lnAnimator.visibility = (View.VISIBLE)
         }
 */
    }

    private fun deleteAll() {
        for (i in dataList.indices) {
            Log.e(TAG, "initControls: " + dataList[i].listingName)
            if (binding.placesData!!.listingName.equals(dataList[i].listingName)) {
                //  Toast.makeText(this, "true", Toast.LENGTH_SHORT).show()
                TodoRoomDatabase.getDatabase(this).todoDao().delete(dataList[i])
                favouriteItem = false
                ivFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)

                // TodoRoomDatabase.getDatabase(this).todoDao().deleteById(dataList[i].Id)

            }

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
        todoEntity.isFav = true
        TodoRoomDatabase.getDatabase(this).todoDao().insertAll(todoEntity)
        dataList11.add((todoEntity))



        ivFav.setImageResource(R.drawable.ic_baseline_favorite_24)
        ivFav.setColorFilter(
            ContextCompat.getColor(this, R.color.my_app_error_color),
            android.graphics.PorterDuff.Mode.SRC_IN
        );

        /* TodoRoomDatabase.getDatabase(this).todoDao().getAll().forEach()
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
             for (i in dataList.indices) {
                 if (dataList.contains(todoEntity)) {
                     Toast.makeText(this, "This place is already in favourite", Toast.LENGTH_LONG)
                         .show()

                 }
                 TodoRoomDatabase.getDatabase(this).todoDao().insertAll(todoEntity)
                 ivFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                 ivFav.setColorFilter(
                     ContextCompat.getColor(this, R.color.my_app_error_color),
                     android.graphics.PorterDuff.Mode.SRC_IN
                 );


             }


         }
 */

    }

    // slide the view from below itself to the current position
    fun slideUp() {
        /*binding.imgAnimator.visibility = View.VISIBLE
        val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce_animation)
        binding.imgAnimator.startAnimation(bounceAnimation)*/
      /*  val slideUpAnimation = AnimationUtils.loadAnimation(
            getApplicationContext(),
            R.anim.slide_up_animation
        );

        binding.lnAnimator.startAnimation(slideUpAnimation)

        binding.lnAnimator.visibility = (View.VISIBLE)*/

    }
}