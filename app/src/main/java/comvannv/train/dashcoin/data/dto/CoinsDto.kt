package comvannv.train.dashcoin.data.dto

import comvannv.train.dashcoin.domain.model.Coins

data class CoinsDto(
    val coins: List<Coin>
)

fun CoinsDto.toCoins(): Coins = Coins(coins = this.coins.map { it.toCoinDetail() })