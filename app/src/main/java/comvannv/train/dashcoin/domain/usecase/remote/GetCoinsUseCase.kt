package comvannv.train.dashcoin.domain.usecase.remote

import comvannv.train.dashcoin.data.dto.Resource
import comvannv.train.dashcoin.data.dto.toCoinDetail
import comvannv.train.dashcoin.data.dto.toCoins
import comvannv.train.dashcoin.data.remote.DashCoinApi
import comvannv.train.dashcoin.domain.model.Coins
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
class GetCoinsUseCase(private val api: DashCoinApi) {
     operator fun invoke() = flow<Resource<List<Coins>>> {
        try {
            emit(Resource.Loading())
            delay(1000L)
            val response = api.getCoins()
            if (response.isSuccessful) {
                emit(Resource.Success(data = response.body()!!.coins.map { it.toCoins() }))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unexpected Error"))
        }
    }
}