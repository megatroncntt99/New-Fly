package comvannv.train.dashcoin.data.dto

import comvannv.train.dashcoin.domain.model.NewsDetail

data class News(
    val description: String,
    val feedDate: Long,
    val id: String,
    val imgURL: String,
    val isFeatured: Boolean,
    val link: String,
    val reactionsCount: ReactionsCount,
    val relatedCoins: List<String>,
    val shareURL: String,
    val source: String,
    val sourceLink: String,
    val title: String
)

fun News.toNewsDetail(): NewsDetail {
    return NewsDetail(
        description = description,
        id = id,
        imgURL = imgURL,
        link = link,
        relatedCoins = relatedCoins,
        shareURL = shareURL,
        source = source,
        sourceLink = sourceLink,
        title = title
    )
}