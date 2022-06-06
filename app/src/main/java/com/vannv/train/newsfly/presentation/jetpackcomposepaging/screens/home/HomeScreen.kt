package com.vannv.train.newsfly.presentation.jetpackcomposepaging.screens.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.model.UnsplashImage
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.navigation.Screen
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.screens.widget.ListContent

/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/06/2022
 */

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val getAllImages = homeViewModel.getAllImages.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClick = {
                    navController.navigate(Screen.Search.route)
                }
            )
        },
        content = {
            println(it.calculateBottomPadding())
            ListContent(unsplashImages = getAllImages)
        }
    )
}