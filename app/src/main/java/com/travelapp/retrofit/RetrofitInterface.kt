package com.networking.retrofit


import com.google.gson.JsonObject
import com.travelapp.retrofit.PlacesModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query


interface RetrofitInterface {


    //get Videos
    @POST("search")
    fun getPlacesApi(
        @Body
        jsonObject: JsonObject
    ): Call<PlacesModel>


}