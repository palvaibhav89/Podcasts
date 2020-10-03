package com.techknights.podcast.ui.dashboard.adpater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techknights.podcast.model.PodCast
import com.techknights.podcast.view.PodCastView

class PodCastAdapter(val data: ArrayList<PodCast>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var content: PodCastView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        content = PodCastView(parent.context)
        return content.viewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        content.viewHolder = holder as PodCastView.ViewHolder
        content.setData(data[position])
    }
}