package com.vannv.train.newsfly.ui.search


import com.vannv.train.newsfly.di.Local
import com.vannv.train.newsfly.di.Remote
import com.vannv.train.newsfly.network.RequestState
import com.vannv.train.newsfly.network.UiState
import com.vannv.train.newsfly.room.entity.RecentArticle
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
    @Local private val localSearchDataSource: SearchDataSource,
    @Remote private val remoteSearchDataSource: SearchDataSource,
) : SearchRepository {
    override suspend fun getListSearch(key: String): Flow<UiState<List<RecentArticle>>> = flow {
        //Xử lý data local và remote
        emit(UiState())
        val localListings = localSearchDataSource.getListSearch(key)
        emit(UiState(RequestState.SUCCESS, result = localListings))
        val isDbEmpty = localListings.isEmpty() && key.isNotBlank()
        if (!isDbEmpty) return@flow
        val remoteListings = try {
            val response = remoteSearchDataSource.getListSearch(key)
            response
        } catch (e: IOException) {
            e.printStackTrace()
            emit(UiState(RequestState.ERROR, message = e.localizedMessage, code = e.hashCode()))
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(UiState(RequestState.ERROR, message = e.localizedMessage, code = e.hashCode()))
            null
        }
        remoteListings?.let { list ->
            localSearchDataSource.clearList()
            localSearchDataSource.insertList(list)
            emit(
                UiState(
                    RequestState.SUCCESS,
                    result = localSearchDataSource.getListSearch("")
                )
            )

        }

    }
}