package com.vannv.train.newsfly.presentation.jetpackcomposepaging.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.screens.home.HomeScreen

/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/06/2022
 */
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Search.route) {

        }
    }
}