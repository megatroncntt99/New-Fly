package comvannv.train.dashcoin.domain.model

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
enum class FilterNews(val value: String) {
    FILTER_HANDPICKED("handpicked"),
    FILTER_TRENDING("trending"),
    FILTER_LATEST("latest"),
    FILTER_BULLISH("bullish"),
    FILTER_BEARISH("bearish"),
}