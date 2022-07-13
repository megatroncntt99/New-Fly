package comvannv.train.dashcoin.domain.usecase.remote

import comvannv.train.dashcoin.data.dto.Resource
import comvannv.train.dashcoin.data.dto.toCoinDetail
import comvannv.train.dashcoin.data.dto.toCoins
import comvannv.train.dashcoin.data.remote.DashCoinApi
import comvannv.train.dashcoin.domain.model.Coins
import kotlinx.coroutines.flow.flow

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
class GetCoinsUseCase(private val api: DashCoinApi) {
    suspend operator fun invoke() = flow<Resource<Coins>> {
        try {
            emit(Resource.Loading())
            val response = api.getCoins()
            if (response.isSuccessful) {
                emit(Resource.Success(data = response.body()!!.toCoins()))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unexpected Error"))
        }
    }
}