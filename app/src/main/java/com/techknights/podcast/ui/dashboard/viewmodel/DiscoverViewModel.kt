package com.techknights.podcast.ui.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.techknights.podcast.ui.dashboard.repository.DiscoverApiRepository
import com.techknights.podcast.utils.Resource
import kotlinx.coroutines.Dispatchers

class DiscoverViewModel : ViewModel() {

    private val repository = DiscoverApiRepository()

    fun getBanners() = repository.getBanners()

    fun getPodCasts() = repository.getPodCasts()
}