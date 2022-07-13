package comvannv.train.dashcoin.domain.usecase.remote

import comvannv.train.dashcoin.data.dto.Resource
import comvannv.train.dashcoin.data.dto.toChart
import comvannv.train.dashcoin.data.remote.DashCoinApi
import comvannv.train.dashcoin.domain.model.ChartTimeSpan
import comvannv.train.dashcoin.domain.model.Charts
import comvannv.train.dashcoin.domain.model.CoinById
import kotlinx.coroutines.flow.flow
import java.lang.Exception

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
class GetChartUseCase(private val api: DashCoinApi) {
    suspend operator fun invoke(coinId: String, chartTimeSpan: ChartTimeSpan) = flow<Resource<Charts>> {
        try {
            emit(Resource.Loading())
            val response = api.getCharsData(coinId, chartTimeSpan.value)
            if (response.isSuccessful) {
                emit(Resource.Success(data = response.body()?.toChart() ?: Charts()))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unexpected Error"))
        }
    }
}