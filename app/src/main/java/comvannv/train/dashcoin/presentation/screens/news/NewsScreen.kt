package comvannv.train.dashcoin.presentation.screens.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import comvannv.train.dashcoin.presentation.RequestState
import comvannv.train.dashcoin.presentation.components.NewsItem
import comvannv.train.dashcoin.presentation.components.SearchBar
import comvannv.train.dashcoin.presentation.components.TopBar
import comvannv.train.dashcoin.presentation.ui.theme.CustomRed
import comvannv.train.dashcoin.presentation.ui.theme.DarkGray

/**
 * Author: vannv8@fpt.com.vn
 * Date: 15/07/2022
 */
@Composable
fun NewsScreen(newsViewModel: NewsViewModel = hiltViewModel()) {
    val newsState = newsViewModel.stateNews.value
    val searchNews = remember { mutableStateOf(TextFieldValue("")) }
    val uriHandler = LocalUriHandler.current
    val searchCoin = remember { mutableStateOf(TextFieldValue("")) }
    val isRefreshing by newsViewModel.isRefresh.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
            .padding(all = 12.dp)
    ) {
        Column {
            TopBar(title = "Trending News")
            SearchBar(hint = "Search...", state = searchNews, modifier = Modifier.fillMaxWidth())
            Row(modifier = Modifier.padding(12.dp)) {
                val isBeingSearched = searchCoin.value.text
                SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = isRefreshing), onRefresh = { newsViewModel.refresh() }) {
                    LazyColumn {
                        items(newsState.result?.filter {
                            it.title.contains(isBeingSearched, ignoreCase = true) || it.description.contains(
                                isBeingSearched,
                                ignoreCase = true
                            )
                        } ?: emptyList(), key = { it.title }) { news ->
                            NewsItem(newsDetail = news) {
                                uriHandler.openUri(news.link)
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                    }
                }
            }
        }
        if (newsState.state == RequestState.NON) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = CustomRed)
        }
        if (newsState.state == RequestState.ERROR) {
            Text(
                text = newsState.message.orEmpty(),
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