package com.techknights.podcast.model

data class Comment(
    var podcast_id: Int? = null,
    var comment: String? = null,
    var comment_child_count: Int? = null,
    var comment_id: Int? = null,
    var comment_like_count: Int? = null,
    var comment_share_count: Int? = null,
    var episode: Episode? = null,
    var post_date: String? = null,
    var user: User? = null
)