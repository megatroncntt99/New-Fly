package comvannv.train.dashcoin.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import comvannv.train.dashcoin.domain.model.CoinById
import comvannv.train.dashcoin.domain.model.Coins
import comvannv.train.dashcoin.presentation.ui.theme.Gold
import comvannv.train.dashcoin.presentation.ui.theme.LighterGray
import comvannv.train.dashcoin.presentation.ui.theme.TextWhite

/**
 * Author: vannv8@fpt.com.vn
 * Date: 15/07/2022
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WatchListItem(coin: Coins, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 10.dp),
        elevation = 8.dp,
        backgroundColor = LighterGray,
        onClick = onClick
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 28.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.Start, modifier = Modifier.weight(0.7F)) {
                    AsyncImage(model = coin.icon, contentDescription = null, modifier = Modifier.size(40.dp))
                }
                Column(modifier = Modifier.weight(3F)) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Statics", color = TextWhite, fontWeight = FontWeight.Bold)
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = null,
                            tint = TextWhite,
                            modifier = Modifier
                                .graphicsLayer(scaleX = 0.8F, scaleY = 0.8F)
                                .padding(start = 5.dp)
                        )
                    }
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(8F), horizontalAlignment = Alignment.Start) {
                    Text(
                        text = coin.name,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Column(modifier = Modifier.weight(3F)) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(5.dp))
                                .background(LighterGray)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${coin.rank}", style = MaterialTheme.typography.body2,
                                fontWeight = FontWeight.Bold,
                                color = Gold,
                            )
                        }
                        Text(
                            text = coin.symbol,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .align(Alignment.Bottom)
                        )
                    }
                }

            }
        }
    }
}