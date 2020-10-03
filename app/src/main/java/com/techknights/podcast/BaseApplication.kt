package com.techknights.podcast

import android.app.Application
import android.content.Context
import com.techknights.podcast.rest.ApiError
import com.techknights.podcast.rest.ServerInterface
import com.techknights.podcast.utils.NetworkUtils
import com.techknights.podcast.utils.PrefUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

class BaseApplication : Application() {

    companion object {
        lateinit var sharedPref: PrefUtils
        private lateinit var ctx: Context

        fun <T> doServerCall(
            method: suspend () -> Response<T>?,
            serverInterface: ServerInterface<T>
        ): Job? {

            if (!NetworkUtils.isNetworkConnected(ctx)) {
                serverInterface.onCustomError(
                    ApiError(
                        ctx.resources?.getString(R.string.network_error) ?: "",
                        ApiError.NO_INTERNET
                    )
                )
                return null
            }
            return CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = method.invoke()

                    if (response?.isSuccessful == true && response.body() != null) {
                        serverInterface.onSuccess(response.body()!!)
                    } else {
                        handleError(response, serverInterface)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    serverInterface.onError(e)
                }
            }
        }

        private fun <T> handleError(response: Response<T>?, serverInterface: ServerInterface<T>) {
            val stringJson = response?.errorBody()?.string()

            serverInterface.onCustomError(
                ApiError(
                    ctx.resources?.getString(R.string.generic_error) ?: "",
                    response?.code() ?: -1,
                    stringJson
                )
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
        sharedPref = PrefUtils(ctx)
    }
}