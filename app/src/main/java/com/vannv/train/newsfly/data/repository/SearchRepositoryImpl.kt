package com.vannv.train.newsfly.data.repository


import com.vannv.train.newsfly.data.datasource.search.LocalSearchDataSource
import com.vannv.train.newsfly.data.datasource.search.RemoteSearchDataSource
import com.vannv.train.newsfly.data.remote.ResultWrapper
import com.vannv.train.newsfly.di.Local
import com.vannv.train.newsfly.di.Remote
import com.vannv.train.newsfly.domain.entity.RecentArticle
import com.vannv.train.newsfly.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Creator: Nguyen Van Van
 * Date: 06,May,2022
 * Time: 3:17 PM
 */
@Singleton
class SearchRepositoryImpl @Inject constructor(
    @Local private val localSearchDataSource: LocalSearchDataSource,
    @Remote private val remoteSearchDataSource: RemoteSearchDataSource,
) : SearchRepository {
    override suspend fun getListSearch(key: String): Flow<ResultWrapper<List<RecentArticle>>> =
        flow {
            //Xử lý data local và remote
            val localListings = localSearchDataSource.getListSearch(key)
            emit(ResultWrapper.Success(value = localListings))
            val isDbEmpty = localListings.isEmpty() && key.isNotBlank()
            if (!isDbEmpty) return@flow
            val remoteListings = try {
                val response = remoteSearchDataSource.getListSearch(key)
                response
            } catch (e: IOException) {
                e.printStackTrace()
                emit(ResultWrapper.Error(error = e.message ?: "", message = e.localizedMessage, code = e.hashCode()))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(ResultWrapper.Error(error = e.message ?: "", message = e.localizedMessage, code = e.hashCode()))
                null
            }
            remoteListings?.let { list ->
                localSearchDataSource.clearList()
                localSearchDataSource.insertList(list)
                emit(ResultWrapper.Success(value = localSearchDataSource.getListSearch("")))
            }

        }
}