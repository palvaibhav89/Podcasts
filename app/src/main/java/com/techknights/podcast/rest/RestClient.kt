package com.techknights.podcast.rest

import com.techknights.podcast.BuildConfig
import com.techknights.podcast.helper.GlobalConstants
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestClient {
    companion object{
        fun <S> createService(serviceClass: Class<S>): S {
            return getRetrofit(BuildConfig.BASE_URL)!!.create(serviceClass)
        }

        private fun getRetrofit(baseUrl: String): Retrofit? {
            return Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getBuilder())
                    .build()
        }

        private fun getBuilder(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
            val httpClient = OkHttpClient.Builder()
            httpClient.connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS))
            httpClient.addInterceptor(logging)
            httpClient.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request().newBuilder().addHeader(GlobalConstants.X_API_KEY, GlobalConstants.X_API_KEY_VALUE).build()
                    return chain.proceed(request)
                }
            })
            httpClient.connectTimeout(1, TimeUnit.MINUTES)
            httpClient.readTimeout(1, TimeUnit.MINUTES)
            httpClient.writeTimeout(1, TimeUnit.MINUTES)

            return httpClient.build()
        }
    }
}