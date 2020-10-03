package com.techknights.podcast.ui.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.techknights.podcast.ui.dashboard.repository.DiscoverApiRepository
import com.techknights.podcast.utils.Resource
import kotlinx.coroutines.Dispatchers

class DiscoverViewModel : ViewModel() {

    private val repository = DiscoverApiRepository()

    fun getBanners() = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repository.getBanners()?.body()?.data))
        } catch (exception: Exception) {
            emit(Resource.error(Resource.GENERIC_ERROR, null))
        }
    }

    fun getPodCasts() = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repository.getPodCasts()?.body()?.data))
        } catch (exception: Exception) {
            emit(Resource.error(Resource.GENERIC_ERROR, null))
        }
    }
}