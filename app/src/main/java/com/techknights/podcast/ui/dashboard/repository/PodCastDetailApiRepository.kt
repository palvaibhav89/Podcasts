package com.techknights.podcast.ui.dashboard.repository

import com.techknights.podcast.rest.NetworkRequests

class PodCastDetailApiRepository {
    suspend fun getComments(podCastId: String) = NetworkRequests.getComments(podCastId)

    suspend fun getEpisodes(podCastId: String) = NetworkRequests.getEpisodes(podCastId)

}