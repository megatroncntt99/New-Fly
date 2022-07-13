package comvannv.train.dashcoin.domain.model

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
enum class ChartTimeSpan(val value: String) {
    TIME_SPAN_1DAY("24h"),
    TIME_SPAN_1WEK("1w"),
    TIME_SPAN_1MONTH("1m"),
    TIME_SPAN_3MONTHS("3m"),
    TIME_SPAN_6MONTHS("6m"),
    TIME_SPAN_1YEAR("1y"),
    TIME_SPAN_ALL("all"),
}