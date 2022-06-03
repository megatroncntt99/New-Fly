package com.vannv.train.newsfly.presentation.navigationdrawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vannv.train.newsfly.R

/**
 * Author: vannv8@fpt.com.vn
 * Date: 02/06/2022
 */

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Header", fontSize = 60.sp)
    }
}

@Composable
fun DrawerBody(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (MenuItem) -> Unit
) {
    LazyColumn {
        items(items) { item ->
            Row(modifier = modifier
                .fillMaxWidth()
                .clickable { onItemClick(item) }
                .padding(16.dp)
            ) {
                Icon(imageVector = item.icon, contentDescription = item.contextDescription)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = item.title, modifier = Modifier.weight(1F), style = itemTextStyle)
            }
            item.run {
            }
        }
    }
}

@Composable
fun AppBar(
    onNavigationClick: () -> Unit
) {
    TopAppBar(
        title =
        { Text(text = stringResource(id = R.string.app_name)) },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Toggle drawer")
            }
        }
    )
}

