package comvannv.train.dashcoin.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import comvannv.train.dashcoin.R
import comvannv.train.dashcoin.presentation.ui.theme.CustomGreen
import comvannv.train.dashcoin.presentation.ui.theme.CustomRed
import comvannv.train.dashcoin.presentation.ui.theme.TextWhite

/**
 * Author: vannv8@fpt.com.vn
 * Date: 15/07/2022
 */


@Composable
fun MakerStatusBar(
    modifier: Modifier = Modifier,
    marketStatus1h: Double,
    marketStatus24h: Double,
    marketStatus1w: Double,
) {
    Row(modifier = modifier) {
        MakerStatusItem(title = "1h", marketStatus = marketStatus1h, modifier = Modifier.weight(1F))
        MakerStatusItem(title = "24h", marketStatus = marketStatus24h, modifier = Modifier.weight(1F))
        MakerStatusItem(title = "1w", marketStatus = marketStatus1w, modifier = Modifier.weight(1F))
    }
}

@Composable
fun MakerStatusItem(
    modifier: Modifier = Modifier,
    title: String,
    marketStatus: Double
) {
    Column(modifier = modifier.padding(5.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h4,
                color = TextWhite
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = if (marketStatus > 0) R.drawable.ic_arrow_positive else R.drawable.ic_arrow_negative),
                contentDescription = null,
                modifier = Modifier
                    .size(12.dp)
                    .padding(end = 4.dp)
            )
            Text(
                text = "$marketStatus",
                style = MaterialTheme.typography.body2,
                color = if (marketStatus > 0) CustomGreen else CustomRed,
                modifier = Modifier
            )
        }
    }
}