package com.vannv.train.newsfly.presentation.jetpackcomposepaging.screens.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/06/2022
 */
@Composable
fun SearchTopBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .semantics {
                contentDescription = "SearchWidget"
            },
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.secondary
    ) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "TextField" },
            placeholder = {
                Text(
                    text = "Search here...",
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    color = Color.White
                )
            },
            textStyle = TextStyle(color = Color.White),
            maxLines = 1,
            leadingIcon = {
                IconButton(onClick = { }, modifier = Modifier.alpha(ContentAlpha.medium)) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Icon Search",
                        tint = Color.DarkGray
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = onCloseClick,
                    modifier = Modifier.semantics { contentDescription = "Icon Close" }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Icon Close")
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchClicked(text)
            }),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                cursorColor = Color.Black,
            )
        )
    }
}

@Preview
@Composable
fun PreviewSearchTopBar() {
    SearchTopBar(text = "Search", onTextChange = {}, onSearchClicked = {}, onCloseClick = {})
}