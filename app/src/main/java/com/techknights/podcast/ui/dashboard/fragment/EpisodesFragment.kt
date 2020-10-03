package com.techknights.podcast.ui.dashboard.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.techknights.podcast.R
import com.techknights.podcast.model.Episode
import com.techknights.podcast.model.PodCast
import com.techknights.podcast.ui.dashboard.adpater.EpisodeAdapter
import com.techknights.podcast.ui.dashboard.viewmodel.PodCastDetailViewModel
import com.techknights.podcast.utils.ResponseStatus
import com.techknights.podcast.view.ErrorView
import kotlinx.android.synthetic.main.fragment_episodes.*

class EpisodesFragment : Fragment() {

    companion object {
        private const val POD_CAST_DATA = "POD_CAST_DATA"

        fun newInstance(podCast: PodCast) : EpisodesFragment {
            val fragment = EpisodesFragment()
            val bundle = Bundle()
            bundle.putParcelable(POD_CAST_DATA, podCast)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var podCast: PodCast? = null
    private var episodeAdapter: EpisodeAdapter? = null
    private var episodes = ArrayList<Episode>()
    private lateinit var viewModel: PodCastDetailViewModel
    private lateinit var ctx: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        podCast = arguments?.getParcelable(POD_CAST_DATA)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(PodCastDetailViewModel::class.java)
        return inflater.inflate(R.layout.fragment_episodes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        errorView.showLoading()

        initViews()

        getEpisodes()
    }

    private fun initViews() {
        episodeAdapter = EpisodeAdapter(episodes)
        episodeRecyclerView.adapter = episodeAdapter

        episodesCount.text = ctx.getString(R.string.episodes, episodes.size)

        errorView.setListener(object : ErrorView.ErrorCallBack {
            override fun onRetryClick() {
                errorView.showLoading()
                getEpisodes()
            }
        })
    }

    private fun getEpisodes() {
        if(podCast?.podcast_id == null)
            return

        viewModel.getEpisodes(podCast?.podcast_id.toString()).observe(this, Observer {
            if(it.status == ResponseStatus.SUCCESS) {
                if(!it.data.isNullOrEmpty()) {
                    episodes.clear()
                    episodes.addAll(it.data)
                    episodeAdapter?.notifyDataSetChanged()
                    episodesCount.text = ctx.getString(R.string.episodes, episodes.size)
                    errorView.hideLoading()
                } else {
                    errorView.showEmptyView()
                }
            } else {
                errorView.setErrorMessage()
            }
        })
    }
}