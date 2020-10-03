package com.techknights.podcast.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PodCast(
    val podcast_id: Int? = null,
    val podcast_author: String? = null,
    val podcast_description: String? = null,
    val podcast_image: String? = null,
    val podcast_title: String? = null,
    val podcast_views: Int? = null,
    val poscast_subscriblers: Int? = null
) : Parcelable