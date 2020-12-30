package com.travelapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.travelapp.data.TravelRepository
import com.travelapp.retrofit.PlacesModel

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    var pageNumber = 1
    val userRepository = TravelRepository()
    var progressVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    var loaderVisibility: MutableLiveData<Boolean> = MutableLiveData(false)

    fun userData() :LiveData<PlacesModel> = userRepository.getPlaces(progressVisibility,pageNumber,loaderVisibility)
}