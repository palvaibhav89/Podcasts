package com.techknights.podcast.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techknights.podcast.R
import com.techknights.podcast.model.PodCast
import com.techknights.podcast.ui.dashboard.activity.PodCastDetailActivity
import com.techknights.podcast.utils.ImageUtils

class PodCastView : FrameLayout {

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
        inflate(context, R.layout.pod_cast_view, this)
        viewHolder = ViewHolder(this)
    }

    fun setData(podCast: PodCast) {
        if(podCast.podcast_image != null) {
            ImageUtils.setImageByUrlAndCache(ctx, viewHolder.podCastImage, podCast.podcast_image)
        }

        if(podCast.podcast_title != null) {
            viewHolder.podCastTitle.text = podCast.podcast_title
        }

        if(podCast.podcast_author != null) {
            viewHolder.podCastAuthor.text = podCast.podcast_author
        }

        viewHolder.podCastView.setOnClickListener {
            PodCastDetailActivity.start(ctx, podCast)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val podCastImage : ImageView = view.findViewById(R.id.podCastImage)
        val podCastTitle : TextView = view.findViewById(R.id.podCastTitle)
        val podCastAuthor : TextView = view.findViewById(R.id.podCastAuthor)
        val podCastView : LinearLayout = view.findViewById(R.id.podCastView)
    }
}