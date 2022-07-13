package comvannv.train.dashcoin.domain.usecase.local

import comvannv.train.dashcoin.data.database.DashCoinDao
import comvannv.train.dashcoin.domain.model.CoinById
import kotlinx.coroutines.flow.Flow

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
class GetAllCoinsUseCase(private val dao: DashCoinDao) {
    operator fun invoke(): Flow<List<CoinById>> = dao.getALlCoins()
}