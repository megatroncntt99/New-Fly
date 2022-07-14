package comvannv.train.dashcoin.presentation.screens.coins

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import comvannv.train.dashcoin.core.utils.LogCat
import comvannv.train.dashcoin.navigation.routes.Screens
import comvannv.train.dashcoin.presentation.RequestState
import comvannv.train.dashcoin.presentation.screens.coins.components.CoinsItem
import comvannv.train.dashcoin.presentation.screens.coins.components.SearchBar
import comvannv.train.dashcoin.presentation.screens.coins.components.TopBar
import comvannv.train.dashcoin.presentation.ui.theme.CustomGreen
import comvannv.train.dashcoin.presentation.ui.theme.CustomRed
import comvannv.train.dashcoin.presentation.ui.theme.DarkGray

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */

@Composable
fun CoinsScreen(
    viewModel: CoinsViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.collectAsState()
    val isRefreshing by viewModel.isRefresh.collectAsState()
    val searchCoin = remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .background(DarkGray)
            .fillMaxSize()
            .padding(top = 10.dp)
    ) {
        Column {
            TopBar(title = "Live Price")
            SearchBar(
                hint = "Search...",
                state = searchCoin,
                modifier = Modifier
                    .fillMaxWidth()
            )

            val isBeingSearched = searchCoin.value.text
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = isRefreshing), onRefresh = { viewModel.refresh() }) {
                LazyColumn {
                    items(items = state.value.result?.filter {
                        it.name.contains(isBeingSearched, ignoreCase = true) ||
                                it.id.contains(isBeingSearched, ignoreCase = true) ||
                                it.symbol.contains(isBeingSearched, ignoreCase = true)
                    } ?: emptyList(), key = { it.id }) { coins ->
                        CoinsItem(coins = coins, onItemClick = {
                            navController.navigate(Screens.CoinDetailScreen.route + "/${coins.id}")
                        })
                    }
                }
            }
        }
        if (state.value.state == RequestState.NON) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = CustomRed)
        }
        if (state.value.state == RequestState.ERROR) {
            state.value.message ?: return
            Text(
                text = state.value.message.orEmpty(),
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
    }
}