package com.travelapp.data

import androidx.paging.PagingSource
import com.google.gson.JsonObject
import com.networking.retrofit.RetrofitClass
import com.travelapp.retrofit.PlacesModel
import com.travelapp.utils.BindingAdapters

class PostDataSource(private val apiService: RetrofitClass) : PagingSource<Int, PlacesModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlacesModel> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            var jsonObj: JsonObject = JsonObject()
            jsonObj.addProperty("pageNumber", currentLoadingPageKey)
            jsonObj.addProperty("pageSize", BindingAdapters.PAGESIZE)

            var finalJson: JsonObject = JsonObject()
            finalJson.addProperty("lat", "37.7749295")
            finalJson.addProperty("lon", "-122.4194155")
            finalJson.add("paginationDto", jsonObj)
            finalJson.addProperty("searchType", "ACTIVITIES")
            finalJson.addProperty("sortBy", "NEAREST")
            val response = apiService.getClient.getPlacesApi(finalJson)
            val responseData = mutableListOf<PlacesModel>()
            val data = response.body()
            responseData.add(data!!)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }
}