package com.techknights.podcast.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techknights.podcast.R
import com.techknights.podcast.model.Comment
import com.techknights.podcast.utils.ImageUtils
import com.techknights.podcast.utils.Utils

class CommentView : FrameLayout {

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
        inflate(context, R.layout.comment_view, this)
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        viewHolder = ViewHolder(this)
    }

    fun setData(comment: Comment) {
        if(!comment.user?.user_name.isNullOrEmpty()) {
            viewHolder.userName.text = comment.user?.user_name
        }
        if(!comment.user?.user_image.isNullOrEmpty()) {
            ImageUtils.setImageWithCircleCrop(ctx, viewHolder.userImage, comment.user?.user_image, R.drawable.ic_user_placeholder)
        }
        if(!comment.post_date.isNullOrEmpty()) {
            viewHolder.postDate.text = Utils.getParsedDate(comment.post_date)
        }
        if(!comment.comment.isNullOrEmpty()) {
            viewHolder.comment.text = comment.comment
        }
        if(!comment.episode?.episode_image.isNullOrEmpty()) {
            ImageUtils.setImageByUrlAndCache(ctx, viewHolder.episodeImage, comment.episode?.episode_image)
        }
        if(!comment.episode?.episode_title.isNullOrEmpty()) {
            viewHolder.episodeTitle.text = comment.episode?.episode_title
        }
        if(comment.comment_child_count != null && comment.comment_child_count != 0) {
            viewHolder.commentCount.text = comment.comment_child_count.toString()
        }
        if(comment.comment_like_count != null && comment.comment_like_count != 0) {
            viewHolder.likeBtn.text = comment.comment_like_count.toString()
        }
        if(comment.comment_share_count != null && comment.comment_share_count != 0) {
            viewHolder.shareCount.text = comment.comment_share_count.toString()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userImage : ImageView = view.findViewById(R.id.userImage)
        val userName : TextView = view.findViewById(R.id.userName)
        val postDate : TextView = view.findViewById(R.id.postDate)
        val comment : TextView = view.findViewById(R.id.comment)
        val episodeImage : ImageView = view.findViewById(R.id.episodeImage)
        val episodeTitle : TextView = view.findViewById(R.id.episodeTitle)
        val commentCount : TextView = view.findViewById(R.id.commentCount)
        val likeBtn : TextView = view.findViewById(R.id.likeBtn)
        val shareCount : TextView = view.findViewById(R.id.shareCount)
    }
}