package comvannv.train.dashcoin.domain.usecase.remote

import comvannv.train.dashcoin.data.dto.*
import comvannv.train.dashcoin.data.remote.DashCoinApi
import comvannv.train.dashcoin.domain.model.Charts
import comvannv.train.dashcoin.domain.model.CoinById
import kotlinx.coroutines.flow.flow

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
class GetCoinUseCase(private val api: DashCoinApi) {
    suspend operator fun invoke(coinId: String) = flow<Resource<CoinById>> {
        try {
            emit(Resource.Loading())
            val response = api.getCoinById(coinId)
            if (response.isSuccessful) {
                emit(Resource.Success(data = response.body()!!.toCoinDetail()))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unexpected Error"))
        }
    }
}