package com.techknights.podcast.ui.dashboard.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.techknights.podcast.R
import com.techknights.podcast.helper.AppBarStateChangeListener
import com.techknights.podcast.helper.RecyclerViewMargin
import com.techknights.podcast.model.PodCast
import com.techknights.podcast.ui.dashboard.adpater.PodCastAdapter
import com.techknights.podcast.ui.dashboard.viewmodel.DiscoverViewModel
import com.techknights.podcast.utils.ResponseStatus
import kotlinx.android.synthetic.main.fragment_discover.*

class DiscoverFragment : Fragment() {

    private lateinit var discoverViewModel: DiscoverViewModel
    private lateinit var ctx: Context
    private var podCastAdapter: PodCastAdapter? = null
    private var podCasts = ArrayList<PodCast>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        discoverViewModel = ViewModelProvider(this).get(DiscoverViewModel::class.java)
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        errorView.showLoading()

        initViews()

        getBanners()

        getPodCasts()
    }

    private fun initViews() {
        podCastAdapter = PodCastAdapter(podCasts)
        topShowsRootTitle.getRecyclerView().layoutManager = GridLayoutManager(ctx, 2, RecyclerView.HORIZONTAL, false)
        topShowsRootTitle.getRecyclerView().adapter = podCastAdapter
        topShowsRootTitle.getRecyclerView().addItemDecoration(RecyclerViewMargin(ctx.resources.getDimensionPixelSize(R.dimen.size_16dp)))

        appBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                if(state == State.COLLAPSED) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorPrimary))
                } else {
                    toolbar.setBackgroundColor(ContextCompat.getColor(ctx, android.R.color.transparent))
                }
            }
        })
    }

    private fun getBanners() {
        discoverViewModel.getBanners().observe(this, Observer {
            if(it?.status == ResponseStatus.SUCCESS && !it.data.isNullOrEmpty()) {
                bannerView.setData(ArrayList(it.data))
            }
        })
    }

    private fun getPodCasts() {
        discoverViewModel.getPodCasts().observe(this, Observer {
            if(it?.status == ResponseStatus.SUCCESS && !it.data.isNullOrEmpty()) {
                podCasts.clear()
                podCasts.addAll(it.data)
                podCastAdapter?.notifyDataSetChanged()
                topShowsRootTitle.visibility = View.VISIBLE
                errorView.hideLoading()
            } else {
                errorView.setErrorMessage()
                topShowsRootTitle.visibility = View.GONE
            }
        })
    }
}