package comvannv.train.dashcoin.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import comvannv.train.dashcoin.data.dto.Coin

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */

@Entity
data class CoinById(
    val availableSupply: Double,
    val icon: String,
    val id: String,
    val marketCap: Double,
    val name: String,
    val price: Double,
    val priceChange1d: Double,
    val priceChange1h: Double,
    val priceChange1w: Double,
    @PrimaryKey
    val rank: Int,
    val symbol: String,
    val totalSupply: Double,
    val twitterUrl: String? = null,
    val volume: Double,
    val websiteUrl: String,
    val priceBtc: Double,
)
fun CoinById.toCoins(): Coins {
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