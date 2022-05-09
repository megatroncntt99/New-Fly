package com.vannv.train.newsfly.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.greenrobot.eventbus.EventBus
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Creator: Nguyen Van Van
 * Date: 09,May,2022
 * Time: 8:40 AM
 */

class NetworkInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        if (!isOnline(context)) {
            EventBus.getDefault().postSticky(NetworkState.NO_INTERNET)
            return chain.proceed(request)
        }
        try {
            val response: Response = chain.proceed(request)
            if (response.isSuccessful) {
                return response
            }
            when (response.code) {
                HttpURLConnection.HTTP_UNAUTHORIZED -> EventBus.getDefault()
                    .postSticky(NetworkState.UNAUTHORISED)
                HttpURLConnection.HTTP_INTERNAL_ERROR -> EventBus.getDefault()
                    .postSticky(NetworkState.INTERNAL_ERROR)
                HttpURLConnection.HTTP_UNAVAILABLE -> EventBus.getDefault()
                    .postSticky(NetworkState.NO_RESPONSE)
                else -> EventBus.getDefault().postSticky(NetworkState.HTTP_ERROR)
            }
            return response
        } catch (e: IOException) {
            EventBus.getDefault().postSticky(NetworkState.NO_RESPONSE)
        }
        return chain.proceed(request)
    }
}

private fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    val result = when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
    return result
}

enum class NetworkState {
    UNAUTHORISED,
    INTERNAL_ERROR,
    NO_RESPONSE,
    HTTP_ERROR,
    NO_INTERNET
}