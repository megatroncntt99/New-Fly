package com.vannv.train.newsfly.presentation.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.android.material.composethemeadapter.MdcTheme
import com.vannv.train.newsfly.domain.entity.New
import com.vannv.train.newsfly.presentation.RequestState
import com.vannv.train.newsfly.utils.handleStateFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Author: vannv8@fpt.com.vn
 * Date: 02/06/2022
 */
@AndroidEntryPoint
class SearchActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MdcTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SearchCompose()
                }
            }
        }
    }
}

@Composable
fun SearchCompose(viewModel: SearchViewModel = viewModel()) {
    val scope = rememberCoroutineScope()
    val state = viewModel.uiNews.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = viewModel.keySearch.collectAsState().value,
            onValueChange = {
                viewModel.keySearch.value = it
            },
            modifier = Modifier.fillMaxWidth()
        )

        when(state.value.state){
            RequestState.NON -> CircularProgressIndicator()
            RequestState.SUCCESS -> {
                state.value.result?.let { news ->
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(news) { item ->
                            NewItem(new = item){
                                println(it.title)
                            }
                        }
                    }
                }
            }
            RequestState.ERROR -> {

            }
        }


    }
}

@Composable
fun NewItem(new: New, onItemClick: (New) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable {
                onItemClick(new)
            }
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = new.urlToImage),
            contentDescription = new.description,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp),
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            modifier = Modifier
                .wrapContentHeight()

        ) {
            Text(
                text = new.title,
                style = TextStyle(fontSize = 15.sp, color = Color.Black),
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = new.publishedAt, style = MaterialTheme.typography.subtitle1)
        }
    }
}