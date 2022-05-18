package com.vannv.train.newsfly.data.remote

import com.google.gson.Gson
import okhttp3.*
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 2:05 PM
 */

class HttpInterceptorHeader(private val repository: RepositoryToken, private val authUrl: String) :
    Interceptor {
    companion object {
        const val API_KEY = "apiKey"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val currentToken = repository.getToken()
        val originalRequest = chain.request()
        return if (!currentToken.hasExpired()) {// Check Token hết hạn chưa
            chain.proceedDeletingTokenOnError(
                chain.request().newBuilder().addHeaders(currentToken).build()
            )
        } else {
            val refreshTokenRequest =
                originalRequest
                    .newBuilder()
                    .get()
                    .url(authUrl)//url để renew lại token
                    .addHeaders(currentToken)
                    .build()
            val refreshResponse = chain.proceedDeletingTokenOnError(refreshTokenRequest)

            if (refreshResponse.isSuccessful) {
                val refreshedToken = Gson().fromJson(
                    refreshResponse.body?.string(),
                    String::class.java
                )//get token
                repository.saveTiendeoToken(refreshedToken)
                val newCall = originalRequest.newBuilder().addHeaders(refreshedToken).build()
                chain.proceedDeletingTokenOnError(newCall)

            } else chain.proceedDeletingTokenOnError(chain.request())
        }


    }

    private fun Interceptor.Chain.proceedDeletingTokenOnError(request: Request): Response {
        val response = proceed(request)
        if (response.code == HTTP_UNAUTHORIZED) {
            repository.deleteToken()
        }
        return response
    }

    private fun Request.Builder.addHeaders(token: String) =
        this.apply { header("Authorization", "Bearer $token") }

    private fun String.hasExpired(): Boolean =
        true


}

class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        // Review token khi token hết hạn bằng một api bất động bộ
        val newAccessToken: String = "service.refreshToken()";

        // Add new header to rejected request and retry it
        return response.request.newBuilder()
            .header("AUTHORIZATION", newAccessToken)
            .build();
    }
}