package com.vannv.train.newsfly.presentation.jetpackcomposepaging.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.screens.home.HomeViewModel
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.screens.widget.ListContent
import com.vannv.train.newsfly.presentation.search.SearchViewModel

/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/06/2022
 */

@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: HomeViewModel = hiltViewModel()
) {
    val searchQuery by searchViewModel.searchQuery
    val searchImages = searchViewModel.searchImages.collectAsLazyPagingItems()
    Scaffold(topBar = {
        SearchTopBar(
            text = searchQuery,
            onTextChange = {
                searchViewModel.updateSearchQuery(query = it)
            },
            onSearchClicked = {
                searchViewModel.searchHeroes(query = it)
            },
            onCloseClick = {
                navController.popBackStack()
            })
    }) { paddingValues ->
        ListContent(unsplashImages = searchImages)
    }
}