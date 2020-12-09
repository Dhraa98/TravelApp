package com.travelapp.data

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.networking.retrofit.RetrofitClass
import com.travelapp.retrofit.PlacesModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TravelRepository {
    private  val TAG = "TravelRepository"
    var dataValue: MutableLiveData<PlacesModel> = MutableLiveData()
    fun getPlaces(progressVisibility: MutableLiveData<Boolean>): LiveData<PlacesModel> {
        progressVisibility.value = true
        var jsonObj: JsonObject = JsonObject()
        jsonObj.addProperty("pageNumber", 1)
        jsonObj.addProperty("pageSize", 10)

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


                    if (response.body()!!.apiStatus.equals("Success")) {
                        if (response.body()!!.rows!!.size > 0) {
                            dataValue.value = response.body()

                        }else{
                            Log.e(TAG, "onResponse: " )
                        }
                    }else{
                        Log.e(TAG, "onResponse: " )
                    }
                }else{
                    Log.e(TAG, "onResponse:  "+response.message() )
                }
                progressVisibility.value = false

            }

            override fun onFailure(call: Call<PlacesModel>?, t: Throwable?) {
                progressVisibility.value = false
                //  Toast.makeText(context, t!!.message, Toast.LENGTH_SHORT).show()
            }
        })
        return dataValue
    }
}