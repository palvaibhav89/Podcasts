package com.techknights.podcast.helper

import android.graphics.Rect
import android.view.View

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewMargin constructor(private val spacing: Int, private var displayMode: Int = -1) : RecyclerView.ItemDecoration() {

    constructor(spacing: Int, displayMode: Int, startPosition: Int) : this(spacing, displayMode) {
        this.startPosition = startPosition
    }

    private var startPosition = -1

    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1
        const val GRID = 2
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildViewHolder(view).adapterPosition
        if(startPosition != -1 && position < startPosition)
            return
        val itemCount = state.itemCount
        val layoutManager = parent.layoutManager
        setSpacingForDirection(outRect, layoutManager, position, itemCount)
    }

    private fun setSpacingForDirection(outRect: Rect, layoutManager: RecyclerView.LayoutManager?, position: Int, itemCount: Int) {

        if (displayMode == -1) {
            displayMode = resolveDisplayMode(layoutManager)
        }
        when (displayMode) {
            HORIZONTAL -> {
                outRect.left = spacing
                outRect.right = if (position == itemCount - 1) spacing else 0
                outRect.top = spacing
                outRect.bottom = spacing
            }
            VERTICAL -> {
                outRect.left = spacing
                outRect.right = spacing
                outRect.top = spacing
                outRect.bottom = if (position == itemCount - 1) spacing else 0
            }

            GRID -> if (layoutManager is GridLayoutManager) {
                val gridLayoutManager = layoutManager as GridLayoutManager?
                val cols = gridLayoutManager!!.spanCount
                val rows = itemCount / cols

                val pos = if(startPosition != -1) if(position - startPosition < 0) 0 else position - startPosition else position

                outRect.left = spacing
                outRect.right = if (pos % cols == cols - 1) spacing else 0
                outRect.top = spacing
                outRect.bottom = if (pos / cols == rows - 1) spacing else 0
            }
        }
    }

    private fun resolveDisplayMode(layoutManager: RecyclerView.LayoutManager?): Int {
        if (layoutManager is GridLayoutManager) return GRID
        return if (layoutManager!!.canScrollHorizontally()) HORIZONTAL else VERTICAL
    }
}