package com.techknights.podcast.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.techknights.podcast.R
import kotlinx.android.synthetic.main.root_title_recycler.view.*

class RootTitleRecycler : FrameLayout {

    private lateinit var ctx: Context

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context) {
        ctx = context
        inflate(context, R.layout.root_title_recycler, this)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        ctx = context
        inflate(context, R.layout.root_title_recycler, this)

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.RootTitleRecycler, 0, 0)

        val headerStr = a.getString(R.styleable.RootTitleRecycler_headingText)
        if (!headerStr.isNullOrEmpty())
            setHeaderText(headerStr)

        val headerBackground = a.getResourceId(R.styleable.RootTitleRecycler_rootBackground, -1)
        if (headerBackground != -1)
            setHeaderBackground(headerBackground)

        val headerSize = a.getDimensionPixelSize(R.styleable.RootTitleRecycler_headingSize, resources.getDimension(R.dimen.size_12sp).toInt())
        setHeaderSize(headerSize)

        val headingPaddingTop = a.getDimensionPixelSize(R.styleable.RootTitleRecycler_headingPaddingTop, resources.getDimension(R.dimen.size_8dp).toInt())
        setHeadingPaddingTop(headingPaddingTop)

        val headingPaddingBottom = a.getDimensionPixelSize(R.styleable.RootTitleRecycler_headingPaddingBottom, resources.getDimension(R.dimen.size_8dp).toInt())
        setHeadingPaddingBottom(headingPaddingBottom)

        val listPaddingStart = a.getDimensionPixelSize(R.styleable.RootTitleRecycler_listPaddingStart, 0)
        setListPaddingStart(listPaddingStart)

        val listPaddingEnd = a.getDimensionPixelSize(R.styleable.RootTitleRecycler_listPaddingEnd, 0)
        setListPaddingEnd(listPaddingEnd)

        val listPaddingBottom = a.getDimensionPixelSize(R.styleable.RootTitleRecycler_listPaddingBottom, 0)
        setListPaddingBottom(listPaddingBottom)

        val headerTextColor = a.getColor(R.styleable.RootTitleRecycler_headingTextColor, -5)
        if (headerTextColor != -5)
            setHeaderTextColor(headerTextColor)

        val headerCaps = a.getBoolean(R.styleable.RootTitleRecycler_headingCaps, false)
        if (headerCaps)
            setHeaderCaps(headerCaps)

        val showSeeAll = a.getBoolean(R.styleable.RootTitleRecycler_showMore, false)
        showMore(showSeeAll)
    }

    internal fun setHeaderCaps(headerCaps: Boolean) {
        headerText.isAllCaps = headerCaps
    }

    fun showMore(showMore: Boolean) {
        if (showMore) {
            more.visibility = View.VISIBLE
        } else {
            more.visibility = View.GONE
        }
    }

    internal fun setHeaderText(text: String? = "") {
        headerText.text = text
    }

    internal fun setHeaderSize(size: Int) {
        headerText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size.toFloat())
    }

    private fun setHeadingPaddingTop(padding: Int) {
        headerText.setPadding(headerText.paddingLeft, padding, headerText.paddingRight, headerText.paddingBottom)
    }

    private fun setHeadingPaddingBottom(padding: Int) {
        headerText.setPadding(headerText.paddingLeft, headerText.paddingTop, headerText.paddingRight, padding)
    }

    private fun setListPaddingStart(padding: Int) {
        recylerView.setPadding(padding, recylerView.paddingTop, recylerView.paddingRight, recylerView.paddingBottom)
    }

    private fun setListPaddingEnd(padding: Int) {
        recylerView.setPadding(recylerView.paddingLeft, recylerView.paddingTop, padding, recylerView.paddingBottom)
    }

    private fun setListPaddingBottom(padding: Int) {
        recylerView.setPadding(recylerView.paddingLeft, recylerView.paddingTop, recylerView.paddingRight, padding)
    }

    private fun setHeaderTextColor(color: Int) {
        headerText.setTextColor(color)
    }

    internal fun getRecyclerView(): RecyclerView {
        return recylerView
    }

    internal fun setStartMargin(startMargin: Int) {
        val params = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        params.setMargins(startMargin, 0, 0, 0)
        recylerView.layoutParams = params
    }

    private fun setHeaderBackground(color: Int) {
        rootTitleLayout.setBackgroundColor(ContextCompat.getColor(ctx, color))
    }

    fun setHeaderVisibility(visibility: Int) {
        headerText.visibility = visibility
    }

    internal fun setOnRightButtonClickListener(text: String, listener: RightButtonClickListener) {
        more.text = text
        more.setOnClickListener {
            listener.onRightButtonClickListener()
        }
    }

    interface RightButtonClickListener {
        fun onRightButtonClickListener()
    }
}