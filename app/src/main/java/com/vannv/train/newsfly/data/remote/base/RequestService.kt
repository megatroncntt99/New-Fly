package com.vannv.train.newsfly.data.remote.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.gson.JsonElement
import com.vannv.train.newsfly.data.remote.dto.BaseDTO
import com.vannv.train.newsfly.utils.LogCat
import org.greenrobot.eventbus.EventBus
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject

/**
 * Author: vannv8@fpt.com.vn
 * Date: 20/05/2022
 */

class RequestService @Inject constructor(private val apiService: ApiService, val context: Context) {

    var work: Work? = null

    /*
    * Nhớ tất cả request thành công thì phải 200
    * còn lỗi trong trường hợp BE xử lý sai gì đó mà vẫn ghi nhận thành công thì 200 + code lỗi
    * các lỗi còn lại theo HTTP code
    * Sử dụng EventBus để bắn 1 thread về activity cho các case lỗi
    * */

    suspend fun request(repo: Repo) {
        try {
            check(isOnline()) {
                EventBus.getDefault().postSticky(TypeError.NO_INTERNET)
            }
            when (repo.typeRepo) {
                TypeRepo.GET -> get(repo)
                TypeRepo.POST -> post(repo)
                TypeRepo.PUT -> put(repo)
                TypeRepo.DELETE -> delete(repo)
            }
        } catch (e: Exception) {
            LogCat.e(e.message)
            work?.onError(ResultWrapper.Error(error = e.message.toString()))
            EventBus.getDefault().postSticky(TypeError.NO_INTERNET)
        }
    }

    private suspend fun get(repo: Repo) {
        val response = apiService.get(
            url = repo.url,
            params = repo.codeRequired as Map<String, String>? ?: HashMap(),
            headers = repo.headers
        )

        if (response.isSuccessful) work?.onSuccess(ResultWrapper.Success(BaseDTO(data = response.body())))
        else checkCodeAErrorApi(response)
    }

    private suspend fun post(repo: Repo) {
        val response = apiService.post(
            url = repo.url,
            body = repo.codeRequired,
            headers = repo.headers
        )

        if (response.isSuccessful) work?.onSuccess(ResultWrapper.Success(BaseDTO(data = response.body())))
        else checkCodeAErrorApi(response)
    }

    private suspend fun put(repo: Repo) {
        val response = apiService.put(
            url = repo.url,
            body = repo.codeRequired,
            headers = repo.headers
        )

        if (response.isSuccessful) work?.onSuccess(ResultWrapper.Success(BaseDTO(data = response.body())))
        else checkCodeAErrorApi(response)
    }

    private suspend fun delete(repo: Repo) {
        val response = apiService.delete(
            url = repo.url,
            params = repo.codeRequired as Map<String, String>?,
            headers = repo.headers
        )

        if (response.isSuccessful) work?.onSuccess(ResultWrapper.Success(BaseDTO(data = response.body())))
        else  checkCodeAErrorApi(response)
    }

    private fun isOnline(): Boolean {
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

    fun work(work: Work) = apply {
        this.work = work
    }

    inline fun work(
        crossinline onSuccess: suspend (success: ResultWrapper.Success<BaseDTO>) -> Unit = {},
        crossinline onError: suspend (error: ResultWrapper.Error) -> Unit = {}
    ) = work(object : Work {
        override suspend fun onSuccess(result: ResultWrapper.Success<BaseDTO>): ResultWrapper.Success<BaseDTO> {
            onSuccess.invoke(result)
            return result
        }

        override suspend fun onError(error: ResultWrapper.Error): ResultWrapper.Error {
            onError.invoke(error)
            return error
        }
    })

    interface Work {
        suspend fun onSuccess(result: ResultWrapper.Success<BaseDTO>): ResultWrapper.Success<BaseDTO>

        suspend fun onError(error: ResultWrapper.Error): ResultWrapper.Error
    }

    private suspend fun checkCodeAErrorApi(response: Response<JsonElement>) {
        when (response.code()) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                work?.onError(ResultWrapper.Error(error = response.message()))
                EventBus.getDefault().postSticky(TypeError.UNAUTHORISED)
            }
            HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                work?.onError(ResultWrapper.Error(error = response.message()))
                EventBus.getDefault().postSticky(TypeError.INTERNAL_ERROR)
            }

            HttpURLConnection.HTTP_UNAVAILABLE -> {
                work?.onError(ResultWrapper.Error(error = response.message()))
                EventBus.getDefault()
                    .postSticky(TypeError.NO_RESPONSE)
            }
            HttpURLConnection.HTTP_NOT_FOUND -> {
                work?.onError(ResultWrapper.Error(error = response.message()))
            }

            else -> {
                work?.onError(ResultWrapper.Error(error = response.message()))
                EventBus.getDefault().postSticky(TypeError.ANOTHER_ERROR)
            }
        }
    }
}