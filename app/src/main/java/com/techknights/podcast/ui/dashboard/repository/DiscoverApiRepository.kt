package com.techknights.podcast.ui.dashboard.repository

import androidx.lifecycle.MutableLiveData
import com.techknights.podcast.BaseApplication
import com.techknights.podcast.model.OnlyDataList
import com.techknights.podcast.model.PodCast
import com.techknights.podcast.model.TopBanner
import com.techknights.podcast.rest.ApiError
import com.techknights.podcast.rest.NetworkRequests
import com.techknights.podcast.rest.ServerInterface
import com.techknights.podcast.utils.Resource

class DiscoverApiRepository {

    fun getBanners() : MutableLiveData<Resource<List<TopBanner>>> {
        val mutableData = MutableLiveData<Resource<List<TopBanner>>>()

        BaseApplication.doServerCall({NetworkRequests.getBanners()},
            object : ServerInterface<OnlyDataList<TopBanner>> {

            override fun onCustomError(e: ApiError) {
                mutableData.postValue(Resource.error(e.message, null))
            }

            override fun onError(e: Throwable) {
                mutableData.postValue(Resource.error(Resource.GENERIC_ERROR, null))
            }

            override fun onSuccess(data: OnlyDataList<TopBanner>) {
                mutableData.postValue(Resource.success(data.data))
            }
        })

        return mutableData
    }

    fun getPodCasts() : MutableLiveData<Resource<List<PodCast>>> {

        val mutableData = MutableLiveData<Resource<List<PodCast>>>()

        BaseApplication.doServerCall({NetworkRequests.getPodCasts()},
            object : ServerInterface<OnlyDataList<PodCast>> {

            override fun onCustomError(e: ApiError) {
                mutableData.postValue(Resource.error(e.message, null))
            }

            override fun onError(e: Throwable) {
                mutableData.postValue(Resource.error(Resource.GENERIC_ERROR, null))
            }

            override fun onSuccess(data: OnlyDataList<PodCast>) {
                mutableData.postValue(Resource.success(data.data))
            }
        })

        return mutableData
    }
}