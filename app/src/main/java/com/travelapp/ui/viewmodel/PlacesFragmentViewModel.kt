package com.travelapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.networking.retrofit.RetrofitClass
import com.travelapp.data.PostDataSource
import com.travelapp.utils.BindingAdapters

class PlacesFragmentViewModel() : ViewModel() {

    val apiService = RetrofitClass
    val listData = Pager(PagingConfig(pageSize = 10)) {
        PostDataSource(apiService)
    }.flow.cachedIn(viewModelScope)
}