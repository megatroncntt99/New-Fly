package com.vannv.train.newsfly.presentation.navigationdrawer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.appcompattheme.AppCompatTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Author: vannv8@fpt.com.vn
 * Date: 02/06/2022
 */
class NavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCompatTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Surface(modifier = Modifier.fillMaxSize()) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = {
                            AppBar {
                                scope.launch(Dispatchers.Main) {
                                    scaffoldState.drawerState.open()
                                }
                            }
                        },
                        drawerBackgroundColor = MaterialTheme.colors.onSecondary,
                        drawerContentColor = MaterialTheme.colors.secondary,
                        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                        drawerContent = {
                            DrawerHeader()
                            DrawerBody(
                                items = listOf(
                                    MenuItem(
                                        id = "home",
                                        title = "Home",
                                        contextDescription = "Go to home screen",
                                        icon = Icons.Default.Home
                                    ),
                                    MenuItem(
                                        id = "settings",
                                        title = "Settings",
                                        contextDescription = "Go to settings screen",
                                        icon = Icons.Default.Settings
                                    ),
                                    MenuItem(
                                        id = "help",
                                        title = "Help",
                                        contextDescription = "Go to Help screen",
                                        icon = Icons.Default.Info
                                    ),
                                ), onItemClick = {
                                    println(it.contextDescription)
                                    scope.launch(Dispatchers.Main) {
                                        scaffoldState.drawerState.close()
                                    }
                                })
                        }) {
                        it.calculateBottomPadding()
                    }
                }
            }
        }
    }
}

