package com.techknights.podcast.ui.dashboard.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.techknights.podcast.R
import com.techknights.podcast.model.PodCast
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    companion object {
        private const val POD_CAST_DATA = "POD_CAST_DATA"

        fun newInstance(podCast: PodCast) : DetailsFragment {
            val fragment = DetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(POD_CAST_DATA, podCast)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var podCast: PodCast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        podCast = arguments?.getParcelable(POD_CAST_DATA)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!podCast?.podcast_author.isNullOrEmpty()) {
            podCastAuthor.text = podCast?.podcast_author
        }

        if(!podCast?.podcast_description.isNullOrEmpty()) {
            podCastDescription.text = podCast?.podcast_description
        }
    }
}