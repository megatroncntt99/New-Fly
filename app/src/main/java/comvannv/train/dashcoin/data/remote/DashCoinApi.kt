package comvannv.train.dashcoin.data.remote

import comvannv.train.dashcoin.data.dto.ChartDto
import comvannv.train.dashcoin.data.dto.CoinDetailDto
import comvannv.train.dashcoin.data.dto.CoinsDto
import comvannv.train.dashcoin.data.dto.NewsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
interface DashCoinApi {
    @GET("v1/coins")
    suspend fun getCoins(@Query("currency") currency: String = "USD", @Query("skip") skip: Int = 0): Response<CoinsDto>

    @GET("v1/coins/{coinId}")
    suspend fun getCoinById(@Path("coinId") coinId: String): Response<CoinDetailDto>

    @GET("v1/chart")
    suspend fun getCharsData(@Query("coinId") coinId: String, @Query("period") period: String = "24h"): Response<ChartDto>

    @GET("v1/news/{filter}")
    suspend fun getNews(@Path("filter") filter: String, @Query("limit") limit: Int = 20, @Query("skip") skip: Int = 0): Response<NewsDto>
}