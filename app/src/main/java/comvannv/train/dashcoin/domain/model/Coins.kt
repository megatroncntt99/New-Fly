package comvannv.train.dashcoin.domain.model

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
data class Coins(
    val id: String,
    val icon: String,
    val marketCap: Double,
    val name: String,
    val price: Double,
    val priceChange1d: Double,
    val rank: Int,
    val symbol: String
)