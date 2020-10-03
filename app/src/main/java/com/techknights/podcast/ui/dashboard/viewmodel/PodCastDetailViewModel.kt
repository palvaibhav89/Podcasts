package com.techknights.podcast.ui.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techknights.podcast.BaseApplication
import com.techknights.podcast.model.Comment
import com.techknights.podcast.ui.dashboard.repository.PodCastDetailApiRepository
import com.techknights.podcast.utils.Resource
import kotlinx.coroutines.Dispatchers

class PodCastDetailViewModel : ViewModel() {

    private val repository = PodCastDetailApiRepository()

    fun getComments(podCastId: String) = repository.getComments(podCastId)

    fun getEpisodes(podCastId: String) = repository.getEpisodes(podCastId)

    fun getIsSubscribed(podCastId: String) = liveData(Dispatchers.IO) {
        val str = BaseApplication.sharedPref.getSubscribedPodCasts()
        if(str.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<String>>() {}.type
            val subscribedPodCasts : List<String> = Gson().fromJson(str, listType)
            emit(Resource.success(subscribedPodCasts.contains(podCastId)))
        } else {
            emit(Resource.success(false))
        }
    }

    fun subscribe(podCastId: String) = liveData(Dispatchers.IO) {
        val str = BaseApplication.sharedPref.getSubscribedPodCasts()
        val subscribedPodCasts = ArrayList<String>()
        if(str.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<String>>() {}.type
            val list : ArrayList<String> = Gson().fromJson(str, listType)
            subscribedPodCasts.addAll(list)
            if(subscribedPodCasts.contains(podCastId)) {
                subscribedPodCasts.remove(podCastId)
                emit(Resource.success(false))
            } else {
                subscribedPodCasts.add(podCastId)
                emit(Resource.success(true))
            }
        } else {
            subscribedPodCasts.add(podCastId)
            emit(Resource.success(true))
        }
        BaseApplication.sharedPref.saveSubscribedPodCasts(Gson().toJson(subscribedPodCasts))
    }

    fun saveComment(comment: Comment) = liveData(Dispatchers.IO) {
        val str = BaseApplication.sharedPref.getComments()
        val comments = ArrayList<Comment>()
        if(str.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<Comment>>() {}.type
            val list : ArrayList<Comment> = Gson().fromJson(str, listType)
            comments.addAll(list)
        }

        comments.add(comment)
        BaseApplication.sharedPref.saveComments(Gson().toJson(comments))
        emit(Resource.success(true))
    }

    fun getLocallySavedComments(podCastId: String) = liveData(Dispatchers.IO) {
        val str = BaseApplication.sharedPref.getComments()
        if(str.isNotEmpty()) {
            val listType = object : TypeToken<ArrayList<Comment>>() {}.type
            val list : ArrayList<Comment> = Gson().fromJson(str, listType)

            list.filter {
                it.podcast_id.toString() == podCastId
            }.let {
                emit(Resource.success(it))
                return@liveData
            }
        }

        emit(Resource.success(ArrayList()))
    }
}