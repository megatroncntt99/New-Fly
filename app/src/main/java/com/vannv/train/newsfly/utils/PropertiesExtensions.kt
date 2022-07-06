package com.vannv.train.newsfly.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.vannv.train.newsfly.presentation.RequestState
import com.vannv.train.newsfly.presentation.UiState

import kotlinx.coroutines.CoroutineScope

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 11:44 AM
 */
fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun Activity.whiteStatsBar(light: Boolean) {
    window.statusBarColor = Color.TRANSPARENT
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        window.navigationBarColor = if (light) Color.WHITE else Color.BLACK
    } else {
        window.setDecorFitsSystemWindows(false)

        window.navigationBarColor = if (light) Color.WHITE else Color.BLACK
    }
}

fun Activity.setOverlayStatusBar(statusBarColorLight: Boolean) {
    window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    window.statusBarColor = Color.TRANSPARENT
    val lFlags = window.decorView.systemUiVisibility
    window.decorView.systemUiVisibility =
        if (statusBarColorLight) lFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() else lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

fun Context.isDarkThemeOn(): Boolean =
    resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

val <T> T.exhaustive: T
    get() = this

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_Storage")

fun Fragment.launchWhenCreated(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launchWhenCreated { block.invoke(this) }
}

fun AppCompatActivity.launchWhenCreated(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launchWhenCreated { block.invoke(this) }
}

fun <T> handleStateFlow(
    state: UiState<T>,
    onSuccess: (() -> Unit)? = null,
    onError: (() -> Unit)? = null,
    onNon: (() -> Unit)? = null
) {
    when (state.state) {
        RequestState.SUCCESS -> onSuccess?.invoke()
        RequestState.ERROR -> onError?.invoke()
        RequestState.NON -> onNon?.invoke()
    }
}

fun Window.setFullScreen(isNotFullScreen: Boolean = false) {
    WindowCompat.setDecorFitsSystemWindows(this, isNotFullScreen)
}

fun Window.lightStatusBar(isLight: Boolean = true) {
    val wic = WindowInsetsControllerCompat(this, this.decorView)
    wic.isAppearanceLightStatusBars = isLight
}

