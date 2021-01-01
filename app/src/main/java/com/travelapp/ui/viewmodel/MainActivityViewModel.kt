package com.travelapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.networking.retrofit.RetrofitClass
import com.travelapp.retrofit.PlacesModel
import com.travelapp.utils.BindingAdapters
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "MainActivityViewModel"
    var pageNumber = 1

    var progressVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    var loaderVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    var data: MutableLiveData<PlacesModel> = MutableLiveData<PlacesModel>()

    fun userData() {
        getPlaces(progressVisibility, pageNumber, loaderVisibility)
    }

    fun getPlaces(
        progressVisibility: MutableLiveData<Boolean> = MutableLiveData(false),
        pageNumber: Int, loaderVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    ) {
        if (pageNumber == 1) {
            progressVisibility.value = true
        } else {
           loaderVisibility.value = true
        }

        var jsonObj: JsonObject = JsonObject()
        jsonObj.addProperty("pageNumber", pageNumber)
        jsonObj.addProperty("pageSize", BindingAdapters.PAGESIZE)

        var finalJson: JsonObject = JsonObject()
        finalJson.addProperty("lat", "37.7749295")
        finalJson.addProperty("lon", "-122.4194155")
        finalJson.add("paginationDto", jsonObj)
        finalJson.addProperty("searchType", "ACTIVITIES")
        finalJson.addProperty("sortBy", "NEAREST")
        val call: Call<PlacesModel> =
            RetrofitClass.getClient.getPlacesApi(finalJson)

        call.enqueue(object : Callback<PlacesModel> {

            override fun onResponse(
                call: Call<PlacesModel>?,
                response: Response<PlacesModel>?
            ) {
                if (response!!.isSuccessful) {


                    if (response.body()?.apiStatus.equals("Success")) {
                        if (response.body()?.rows!!.size > 0) {
                            data.value = response.body()

                        } else {
                            Timber.e(TAG, "onResponse: ")
                        }
                    } else {
                        Timber.e(TAG, "onResponse: ")
                    }
                } else {
                    Timber.e(TAG, "onResponse:  " + response.message())
                }
                if (pageNumber == 1) {
                    progressVisibility.value = false
                } else {
                    loaderVisibility.value = false
                }

            }

            override fun onFailure(call: Call<PlacesModel>?, t: Throwable?) {
                if (pageNumber == 1) {
                    progressVisibility.value = false
                } else {
                   loaderVisibility.value = false
                }

            }
        })
    }
}