package comvannv.train.dashcoin.domain.usecase.local

import comvannv.train.dashcoin.data.database.DashCoinDao
import comvannv.train.dashcoin.domain.model.CoinById

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
class DeleteCoinUseCase(private val dao: DashCoinDao) {
    suspend operator fun invoke(coinById: CoinById) {
        dao.deleteCoin(coinById)
    }
}