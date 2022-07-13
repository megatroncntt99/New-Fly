package comvannv.train.dashcoin.data.dto

import comvannv.train.dashcoin.domain.model.Charts

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
data class ChartDto(
    val chart: List<List<Float>>
)

fun ChartDto.toChart(): Charts {
    return Charts(
        chart
    )
}
