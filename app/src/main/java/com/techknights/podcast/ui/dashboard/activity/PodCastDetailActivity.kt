package com.techknights.podcast.ui.dashboard.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.appbar.AppBarLayout
import com.techknights.podcast.R
import com.techknights.podcast.helper.AppBarStateChangeListener
import com.techknights.podcast.model.Comment
import com.techknights.podcast.model.PodCast
import com.techknights.podcast.ui.dashboard.adpater.PodCastDetailPagerAdapter
import com.techknights.podcast.ui.dashboard.viewmodel.PodCastDetailViewModel
import com.techknights.podcast.utils.ImageUtils
import com.techknights.podcast.view.LoaderDialog
import kotlinx.android.synthetic.main.activity_pod_cast_detail.*
import org.greenrobot.eventbus.EventBus

class PodCastDetailActivity : AppCompatActivity() {

    private var tabAdapter: PodCastDetailPagerAdapter? = null
    private var podCast: PodCast? = null
    private lateinit var viewModel : PodCastDetailViewModel
    private var isSubscribed = false
    private var loader : LoaderDialog? = null

    companion object {
        private const val POD_CAST_DATA = "POD_CAST_DATA"

        fun start(ctx: Context, podCast: PodCast) {
            val intent = Intent(ctx, PodCastDetailActivity::class.java)
            intent.putExtra(POD_CAST_DATA, podCast)
            ctx.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pod_cast_detail)
        viewModel = ViewModelProvider(this).get(PodCastDetailViewModel::class.java)

        setToolbar()

        getDataFromIntent()

        if(podCast == null) {
            finish()
            return
        }

        initViews()

        getIsSubscribed()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.show_info)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getDataFromIntent() {
        if(intent.hasExtra(POD_CAST_DATA)) {
            podCast = intent.getParcelableExtra(POD_CAST_DATA)
        }
    }

    private fun initViews() {

        loader = LoaderDialog(this)

        tabAdapter = PodCastDetailPagerAdapter(supportFragmentManager, podCast!!)
        viewPager.adapter = tabAdapter
        tabLayout.setupWithViewPager(viewPager)


        appBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                if(state == State.COLLAPSED) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(this@PodCastDetailActivity, R.color.colorPrimary))
                } else {
                    toolbar.setBackgroundColor(ContextCompat.getColor(this@PodCastDetailActivity, android.R.color.transparent))
                }
            }
        })

        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if(tabLayout.getTabAt(position)?.text.toString().equals("comments", true)) {
                    addCommentLayout.visibility = View.VISIBLE
                } else {
                    addCommentLayout.visibility = View.GONE
                }
            }
        })

        addComment.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if(addComment.text.toString().isNotEmpty()) {
                    loader?.show()
                    val comment = Comment()
                    comment.comment = addComment.text.toString()
                    EventBus.getDefault().post(comment)
                    Handler(Looper.getMainLooper()).postDelayed({
                        loader?.hide()
                    }, 1500)
                }
            }
            false
        }

        if(podCast?.podcast_image != null) {
            ImageUtils.setImageByUrlAndCache(this, podCastImage, podCast?.podcast_image)
        }

        if(podCast?.podcast_title != null) {
            podCastTitle.text = podCast?.podcast_title
        }

        if(podCast?.poscast_subscriblers != null) {
            podCastBookMarkCount.text = podCast?.poscast_subscriblers.toString()
        }

        if(podCast?.podcast_views != null) {
            podCastViews.text = podCast?.podcast_views.toString()
        }

        subscribe.setOnClickListener {
            loader?.show()
            subscribe()
            Handler(Looper.getMainLooper()).postDelayed({
                loader?.hide()
            }, 1500)
        }
    }

    private fun getIsSubscribed() {
        viewModel.getIsSubscribed(podCast?.podcast_id.toString()).observe(this, Observer {
            isSubscribed = it?.data ?: false
            setSubscribeButton()
        })
    }

    private fun subscribe() {
        viewModel.subscribe(podCast?.podcast_id.toString()).observe(this, Observer {
            isSubscribed = it?.data ?: false
            setSubscribeButton()
        })
    }

    private fun setSubscribeButton() {
        if(isSubscribed) {
            subscribe.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_small,0,0,0)
            subscribe.text = getString(R.string.subscribed)
        } else {
            subscribe.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_unselected,0,0,0)
            subscribe.text = getString(R.string.subscribe)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.pod_cast_detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }

        return true
    }
}
