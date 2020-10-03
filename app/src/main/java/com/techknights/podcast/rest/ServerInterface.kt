package com.techknights.podcast.rest

interface ServerInterface<T> {
    fun onCustomError(e: ApiError)

    fun onError(e: Throwable)

    fun onSuccess(data: T )
}