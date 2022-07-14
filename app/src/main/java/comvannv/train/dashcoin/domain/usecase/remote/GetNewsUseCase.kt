package comvannv.train.dashcoin.domain.usecase.remote

import comvannv.train.dashcoin.data.dto.Resource
import comvannv.train.dashcoin.data.dto.toChart
import comvannv.train.dashcoin.data.dto.toNewsDetail
import comvannv.train.dashcoin.data.remote.DashCoinApi
import comvannv.train.dashcoin.domain.model.Charts
import comvannv.train.dashcoin.domain.model.FilterNews
import comvannv.train.dashcoin.domain.model.NewsDetail
import kotlinx.coroutines.flow.flow
import java.lang.Exception

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
class GetNewsUseCase(private val api: DashCoinApi) {
     operator fun invoke(filterNews: FilterNews) = flow<Resource<List<NewsDetail>>> {
        try {
            emit(Resource.Loading())
            val response = api.getNews(filterNews.value)
            if (response.isSuccessful) {
                emit(Resource.Success(data = response.body()!!.news.map { it.toNewsDetail() }))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unexpected Error"))
        }
    }
}