package com.techknights.podcast.ui.dashboard.adpater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techknights.podcast.model.Episode
import com.techknights.podcast.view.EpisodeView

class EpisodeAdapter(val data: List<Episode>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var content: EpisodeView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        content = EpisodeView(parent.context)
        return content!!.viewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        content?.viewHolder = holder as EpisodeView.ViewHolder
        content?.setData(data[position])
    }
}