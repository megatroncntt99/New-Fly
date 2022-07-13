package comvannv.train.dashcoin.data.dto

import comvannv.train.dashcoin.domain.model.CoinById

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
data class CoinDetailDto(val coin: Coin)

fun CoinDetailDto.toCoinDetail(): CoinById {
    return CoinById(
        availableSupply = coin.availableSupply,
        icon = coin.icon,
        id = coin.id,
        marketCap = coin.marketCap,
        name = coin.name,
        price = coin.price,
        priceChange1d = coin.priceChange1d,
        priceChange1h = coin.priceChange1h,
        priceChange1w = coin.priceChange1w,
        rank = coin.rank,
        symbol = coin.symbol,
        totalSupply = coin.totalSupply,
        twitterUrl = coin.twitterUrl,
        volume = coin.volume,
        websiteUrl = coin.websiteUrl,
        priceBtc = coin.priceBtc
    )
}