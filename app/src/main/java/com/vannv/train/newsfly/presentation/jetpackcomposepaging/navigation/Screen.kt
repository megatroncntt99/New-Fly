package com.vannv.train.newsfly.presentation.jetpackcomposepaging.navigation

/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/06/2022
 */
sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Search : Screen("search_screen")
}
