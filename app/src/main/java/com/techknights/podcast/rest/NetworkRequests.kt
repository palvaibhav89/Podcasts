package com.techknights.podcast.rest

object NetworkRequests {
    private var request: ApiInterface? = null

    suspend fun getBanners() = request?.getBanners()

    suspend fun getPodCasts() = request?.getPodCasts()

    suspend fun getEpisodes(podCastId: String) = request?.getEpisodes(podCastId)

    suspend fun getComments(podCastId: String) = request?.getComments(podCastId)

    init {
        request = RestClient.createService(ApiInterface::class.java)
    }
}