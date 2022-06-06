package com.vannv.train.newsfly.presentation.jetpackcomposepaging

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.appcompattheme.AppCompatTheme
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.navigation.SetupNavGraph
import dagger.hilt.android.AndroidEntryPoint

/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/06/2022
 */
@AndroidEntryPoint
class PagingActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCompatTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}