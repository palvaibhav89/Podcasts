package com.techknights.podcast.ui.dashboard.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.techknights.podcast.R
import com.techknights.podcast.model.Comment
import com.techknights.podcast.model.Episode
import com.techknights.podcast.model.PodCast
import com.techknights.podcast.model.User
import com.techknights.podcast.ui.dashboard.adpater.CommentAdapter
import com.techknights.podcast.ui.dashboard.viewmodel.PodCastDetailViewModel
import com.techknights.podcast.utils.ResponseStatus
import com.techknights.podcast.utils.Utils
import com.techknights.podcast.view.ErrorView
import kotlinx.android.synthetic.main.fragment_comments.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class CommentsFragment : Fragment() {

    companion object {
        private const val POD_CAST_DATA = "POD_CAST_DATA"

        fun newInstance(podCast: PodCast) : CommentsFragment {
            val fragment = CommentsFragment()
            val bundle = Bundle()
            bundle.putParcelable(POD_CAST_DATA, podCast)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var podCast: PodCast? = null
    private var commentAdapter: CommentAdapter? = null
    private lateinit var viewModel: PodCastDetailViewModel
    private var comments = ArrayList<Comment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        podCast = arguments?.getParcelable(POD_CAST_DATA)
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(PodCastDetailViewModel::class.java)
        return inflater.inflate(R.layout.fragment_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        errorView.showLoading()

        initViews()

        if(podCast?.podcast_id != null) {
            getComments()
        }
    }

    private fun initViews() {
        commentAdapter = CommentAdapter(comments)
        commentRecyclerView.adapter = commentAdapter
        commentRecyclerView.setHasFixedSize(true)

        errorView.setListener(object : ErrorView.ErrorCallBack {
            override fun onRetryClick() {
                errorView.showLoading()
                getComments()
            }
        })
    }

    private fun getComments() {
        viewModel.getComments(podCast?.podcast_id.toString()).observe(this, Observer {
            if(it?.status == ResponseStatus.SUCCESS && !it.data.isNullOrEmpty()) {
                comments.clear()
                comments.addAll(it.data)
            }

            getLocallySavedComments()
        })
    }

    private fun getLocallySavedComments() {
        if(podCast?.podcast_id != null) {
            viewModel.getLocallySavedComments(podCast?.podcast_id.toString()).observe(this, Observer {
                if(!it.data.isNullOrEmpty()) {
                    comments.addAll(it.data)
                    commentAdapter?.notifyDataSetChanged()
                    errorView.hideLoading()
                } else if(comments.isNotEmpty()) {
                    commentAdapter?.notifyDataSetChanged()
                    errorView.hideLoading()
                } else {
                    errorView.showEmptyView()
                }
            })
        }
    }

    private fun saveComment(comment: Comment) {
        if(podCast?.podcast_id != null) {
            comment.podcast_id = podCast?.podcast_id
            comment.comment_child_count = 0
            comment.comment_like_count = 0
            comment.comment_share_count = 0
            comment.post_date = Utils.getCurrentTimeStamp()
            comment.episode = Episode()
            comment.episode?.episode_title = "Example Episode"
            comment.episode?.episode_image =
                "https://image.shutterstock.com/image-vector/best-ic_podcast-ever-illustration-two-600w-1793543458.jpg"
            comment.user = User()
            comment.user?.user_name = "Vaibhav"
            comment.user?.user_image = "https://image.shutterstock.com/image-vector/best-ic_podcast-ever-illustration-two-600w-1793543458.jpg"

            viewModel.saveComment(comment).observe(this, Observer {
                if(it?.data == true) {
                    comments.add(comment)
                    commentAdapter?.notifyDataSetChanged()
                }
            })
        }
    }

    @Subscribe
    fun onCommentEvent(event: Comment) {
        saveComment(event)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
