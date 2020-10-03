package com.techknights.podcast.rest

import com.techknights.podcast.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("banners")
    suspend fun getBanners(): Response<OnlyDataList<TopBanner>>

    @GET("podcasts")
    suspend fun getPodCasts(): Response<OnlyDataList<PodCast>>

    @GET("comments/{id}")
    suspend fun getComments(@Path("id") podCastId : String): Response<OnlyDataList<Comment>>

    @GET("episodes/{id}")
    suspend fun getEpisodes(@Path("id") podCastId : String): Response<OnlyDataList<Episode>>
}