package com.vannv.train.newsfly.network

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONException
import org.json.JSONObject


/**
 * Creator: Nguyen Van Van
 * Date: 05,May,2022
 * Time: 1:52 PM
 */

class HttpInterceptorResponse : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        try {
            var objectResponse = ObjectResponseDefault<Any>()
            objectResponse = if (response.isSuccessful) {
                objectResponse.copy(
                    code = response.code,
                    state = StateResponse.OK,
                    result = response.body
                )
            } else {
                objectResponse.copy(
                    code = response.code,
                    state = StateResponse.ERROR,
                    result = response.body
                )
            }
            val contentType: MediaType? = response.body?.contentType()
            val body: ResponseBody = objectResponse.toString().toResponseBody(contentType)

            return response.newBuilder().body(body).build()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        ThreadA {
            println(it)
        }

        return response
    }
}

data class ObjectResponseDefault<T>(
    val code: Int? = null,
    val state: StateResponse = StateResponse.NON,
    val result: T? = null
) {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}


enum class StateResponse(val state: Int) {
    NON(0),
    OK(1),
    ERROR(-1),
}

fun interface ThreadA {
    fun run(item: String)
}