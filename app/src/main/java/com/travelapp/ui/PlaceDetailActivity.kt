package com.travelapp.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.travelapp.R
import com.travelapp.database.TodoEntity
import com.travelapp.database.TodoRoomDatabase
import com.travelapp.databinding.ActivityPlaceDetailBinding
import com.travelapp.retrofit.PlacesModel
import com.travelapp.ui.viewmodel.MainActivityViewModel
import com.travelapp.utils.BindingAdapters.PLACES_KEY
import com.travelapp.utils.BindingAdapters.dataList
import kotlinx.android.synthetic.main.activity_place_detail.*


class PlaceDetailActivity : Fragment() {
    private val TAG = "PlaceDetailActivity"
    private lateinit var binding: ActivityPlaceDetailBinding


    val args:PlaceDetailActivityArgs by navArgs()
    var dataList11: MutableList<TodoEntity> = mutableListOf()
    private var favouriteItem: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.activity_place_detail, container, false)
        binding.lifecycleOwner = activity
        initControls()
        return binding.root

    }





    private fun initControls() {
        binding.lifecycleOwner = this
        val productData = requireActivity().intent.getSerializableExtra(PLACES_KEY)


       // binding.placesData = productData as PlacesModel.Row?
        arguments?.let {
            val safeArgs = PlaceDetailActivityArgs.fromBundle(it)
            binding.placesData  = safeArgs.myArg
        }

        dataList.clear()
        TodoRoomDatabase.getDatabase(requireContext()).todoDao().getAll().forEach()

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
                    ContextCompat.getColor(requireContext(), R.color.my_app_error_color),
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

        binding.ivBack.setOnClickListener {
            ///   binding.imgAnimator.clearAnimation();
            requireActivity().onBackPressed()
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
                TodoRoomDatabase.getDatabase(requireContext()).todoDao().delete(dataList[i])
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
        TodoRoomDatabase.getDatabase(requireContext()).todoDao().insertAll(todoEntity)
        dataList11.add((todoEntity))



        ivFav.setImageResource(R.drawable.ic_baseline_favorite_24)
        ivFav.setColorFilter(
            ContextCompat.getColor(requireContext(), R.color.my_app_error_color),
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