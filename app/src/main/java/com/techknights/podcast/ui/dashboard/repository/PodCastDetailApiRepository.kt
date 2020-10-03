package com.techknights.podcast.ui.dashboard.repository

import androidx.lifecycle.MutableLiveData
import com.techknights.podcast.BaseApplication
import com.techknights.podcast.model.Comment
import com.techknights.podcast.model.Episode
import com.techknights.podcast.model.OnlyDataList
import com.techknights.podcast.rest.ApiError
import com.techknights.podcast.rest.NetworkRequests
import com.techknights.podcast.rest.ServerInterface
import com.techknights.podcast.utils.Resource

class PodCastDetailApiRepository {

    fun getComments(podCastId: String) : MutableLiveData<Resource<List<Comment>>> {

        val mutableData = MutableLiveData<Resource<List<Comment>>>()

        BaseApplication.doServerCall({NetworkRequests.getComments(podCastId)},
            object : ServerInterface<OnlyDataList<Comment>> {

                override fun onCustomError(e: ApiError) {
                    mutableData.postValue(Resource.error(e.message, null))
                }

                override fun onError(e: Throwable) {
                    mutableData.postValue(Resource.error(Resource.GENERIC_ERROR, null))
                }

                override fun onSuccess(data: OnlyDataList<Comment>) {
                    mutableData.postValue(Resource.success(data.data))
                }
            })

        return mutableData
    }

    fun getEpisodes(podCastId: String) : MutableLiveData<Resource<List<Episode>>> {

        val mutableData = MutableLiveData<Resource<List<Episode>>>()

        BaseApplication.doServerCall({NetworkRequests.getEpisodes(podCastId)},
            object : ServerInterface<OnlyDataList<Episode>> {

                override fun onCustomError(e: ApiError) {
                    mutableData.postValue(Resource.error(e.message, null))
                }

                override fun onError(e: Throwable) {
                    mutableData.postValue(Resource.error(Resource.GENERIC_ERROR, null))
                }

                override fun onSuccess(data: OnlyDataList<Episode>) {
                    mutableData.postValue(Resource.success(data.data))
                }
            })

        return mutableData
    }
}