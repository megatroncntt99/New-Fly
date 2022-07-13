package comvannv.train.dashcoin.domain.usecase

import comvannv.train.dashcoin.domain.usecase.local.DeleteCoinUseCase
import comvannv.train.dashcoin.domain.usecase.local.GetAllCoinsUseCase
import comvannv.train.dashcoin.domain.usecase.local.InsertCoinUseCase
import comvannv.train.dashcoin.domain.usecase.remote.GetChartUseCase
import comvannv.train.dashcoin.domain.usecase.remote.GetCoinUseCase
import comvannv.train.dashcoin.domain.usecase.remote.GetCoinsUseCase
import comvannv.train.dashcoin.domain.usecase.remote.GetNewsUseCase

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
class DashCoinUseCase(
    val getCoins: GetCoinsUseCase,
    val getCoin: GetCoinUseCase,
    val getChart: GetChartUseCase,
    val getNews: GetNewsUseCase,

    // domain/ use_case/ database
    val addCoin: InsertCoinUseCase,
    val deleteCoin: DeleteCoinUseCase,
    val getAllCoins: GetAllCoinsUseCase
)