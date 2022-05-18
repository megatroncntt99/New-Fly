package com.vannv.train.newsfly.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vannv.train.newsfly.data.ApiDataSource
import com.vannv.train.newsfly.data.remote.ApiService
import com.vannv.train.newsfly.data.remote.NetworkInterceptor
import com.vannv.train.newsfly.utils.Urls
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 3:15 PM
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesGson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun providesClient(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder().also { client ->
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addNetworkInterceptor(logging)
//        client.addInterceptor(HttpInterceptorHeader(RepositoryToken(),"abc"))
//        client.addInterceptor(HttpInterceptorResponse())
            client.addInterceptor(NetworkInterceptor(context))
            client.connectTimeout(120, TimeUnit.SECONDS)
            client.readTimeout(120, TimeUnit.SECONDS)
            client.protocols(Collections.singletonList(Protocol.HTTP_1_1))
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(Urls.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


    @Provides
    @Singleton
    fun provideApiData(apiService: ApiService) = ApiDataSource(apiService)

}