package comvannv.train.dashcoin.domain.model

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
data class NewsDetail(
    val description: String,
    val id: String,
    val imgURL: String,
    val link: String,
    val relatedCoins: List<Any>,
    val shareURL: String,
    val source: String,
    val sourceLink: String,
    val title: String
)
