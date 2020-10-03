package com.techknights.podcast.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.techknights.podcast.R
import com.techknights.podcast.model.TopBanner
import com.techknights.podcast.utils.ImageUtils
import java.util.*

class BannerAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var layoutInflater: LayoutInflater? = null
    private var ctx: Context = context
    private var data: List<TopBanner>? = ArrayList()

    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    fun addData(data: List<TopBanner>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater?.inflate(R.layout.banner_view_image_container, null)
        view?.layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        return ViewHolder(view!!)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        data?.get(position)?.image?.let { url ->
            ImageUtils.setImageByUrlAndCache(ctx, (holder as ViewHolder).imageView, url, R.drawable.banner_placeholder)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView : ImageView = view.findViewById(R.id.image)
    }
}
