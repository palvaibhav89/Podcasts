package com.techknights.podcast.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techknights.podcast.R
import com.techknights.podcast.model.Episode
import com.techknights.podcast.utils.ImageUtils

class EpisodeView : FrameLayout {

    private lateinit var ctx: Context
    lateinit var viewHolder: ViewHolder

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context){
        ctx = context
        inflate(context, R.layout.episode_view, this)
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        viewHolder = ViewHolder(this)
    }

    fun setData(episode: Episode) {
        if(!episode.episode_image.isNullOrEmpty()) {
            ImageUtils.setImageByUrlAndCache(ctx, viewHolder.episodeImage, episode.episode_image)
        }
        if(!episode.episode_title.isNullOrEmpty()) {
            viewHolder.episodeTitle.text = episode.episode_title
        }
        if(!episode.episode_duration.isNullOrEmpty()) {
            viewHolder.episodeDuration.text = episode.episode_duration
        }
        if(!episode.episode_date.isNullOrEmpty()) {
            viewHolder.episodeDate.text = episode.episode_date
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val episodeImage: ImageView = view.findViewById(R.id.episodeImage)
        val episodeTitle: TextView = view.findViewById(R.id.episodeTitle)
        val episodeDate: TextView = view.findViewById(R.id.episodeDate)
        val episodeDuration: TextView = view.findViewById(R.id.episodeDuration)
    }
}