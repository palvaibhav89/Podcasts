package com.techknights.podcast.utils

import android.content.Context
import android.content.SharedPreferences
import com.techknights.podcast.helper.GlobalConstants

class PrefUtils(ctx: Context) {
    private var sharedPref : SharedPreferences = ctx.getSharedPreferences(GlobalConstants.SHARED_PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val SUBSCRIBERS = "SUBSCRIBERS"
        private const val COMMENTS = "COMMENTS"
    }

    fun saveSubscribedPodCasts(string: String) {
        val editor = sharedPref.edit()
        editor.putString(SUBSCRIBERS, string)
        editor.apply()
    }

    fun saveComments(string: String) {
        val editor = sharedPref.edit()
        editor.putString(COMMENTS, string)
        editor.apply()
    }

    fun getSubscribedPodCasts() : String {
        return sharedPref.getString(SUBSCRIBERS, "") ?: ""
    }

    fun getComments() : String {
        return sharedPref.getString(COMMENTS, "") ?: ""
    }
}