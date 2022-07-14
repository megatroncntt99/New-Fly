package comvannv.train.dashcoin.data.dto

import comvannv.train.dashcoin.domain.model.CoinById
import comvannv.train.dashcoin.domain.model.Coins

data class Coin(
    val availableSupply: Double,
    val exp: List<String>,
    val icon: String,
    val id: String,
    val marketCap: Double,
    val name: String,
    val price: Double,
    val priceBtc: Double,
    val priceChange1d: Double,
    val priceChange1h: Double,
    val priceChange1w: Double,
    val rank: Int,
    val redditUrl: String,
    val symbol: String,
    val totalSupply: Double,
    val twitterUrl: String,
    val volume: Double,
    val websiteUrl: String
)

fun Coin.toCoins(): Coins {
    return Coins(
        id,
        icon,
        marketCap,
        name,
        price,
        priceChange1d,
        rank,
        symbol
    )
}