package com.techknights.podcast.model

data class Episode(
    val episode_date: String? = null,
    val episode_duration: String? = null,
    val episode_id: Int? = null,
    var episode_image: String? = null,
    var episode_title: String? = null
)