package com.techknights.podcast.ui.dashboard.repository

import com.techknights.podcast.rest.NetworkRequests

class DiscoverApiRepository {
    suspend fun getBanners() = NetworkRequests.getBanners()

    suspend fun getPodCasts() = NetworkRequests.getPodCasts()
}