package com.techknights.podcast.ui.dashboard.adpater

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.techknights.podcast.model.PodCast
import com.techknights.podcast.ui.dashboard.fragment.CommentsFragment
import com.techknights.podcast.ui.dashboard.fragment.DetailsFragment
import com.techknights.podcast.ui.dashboard.fragment.EpisodesFragment

class PodCastDetailPagerAdapter(fm: FragmentManager, private val podCast: PodCast) : FragmentPagerAdapter(fm) {

    private val titles = arrayListOf("Episodes", "Details", "Comments")

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = EpisodesFragment.newInstance(podCast)
            }
            1 -> {
                fragment = DetailsFragment.newInstance(podCast)
            }
            2 -> {
                fragment = CommentsFragment.newInstance(podCast)
            }
        }
        return fragment!!
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}