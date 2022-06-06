package com.vannv.train.newsfly.presentation.jetpackcomposepaging.screens.home

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/06/2022
 */

@Composable
fun HomeTopBar(
    onSearchClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "Home", color = MaterialTheme.colors.secondary)
        },
        backgroundColor = MaterialTheme.colors.background,
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Navigation Search Screen"
                )
            }
        })
}
@Composable
@Preview
fun HomeTopBarPreview(){
    HomeTopBar {
    }
}