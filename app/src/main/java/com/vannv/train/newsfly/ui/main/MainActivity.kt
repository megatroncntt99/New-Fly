package com.vannv.train.newsfly.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.network.NetworkState
import com.vannv.train.newsfly.utils.Utility
import com.vannv.train.newsfly.utils.launchWhenCreated
import com.vannv.train.newsfly.utils.setOverlayStatusBar
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_NewsFly)
        super.onCreate(savedInstanceState)
        checkSelectedTheme()
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)
    }

    private fun checkSelectedTheme() {
        launchWhenCreated {
            viewModel.selectedTheme.collect {
                when (it) {
                    getString(R.string.light_mode) -> {
                        AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_NO
                        )
                        setOverlayStatusBar(false)
                    }
                    getString(R.string.dark_mode) -> {
                        AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_YES
                        )
                        setOverlayStatusBar(true)
                    }
                    else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: Any) {
        if (event is NetworkState) {
            when (event) {
                NetworkState.UNAUTHORISED -> {}
                NetworkState.INTERNAL_ERROR -> {}
                NetworkState.NO_RESPONSE -> {
                    Utility.displayErrorSnackBar(
                        findViewById<View>(android.R.id.content).rootView,
                        "Không có response",
                        this
                    )
                }
                NetworkState.HTTP_ERROR -> {}
                NetworkState.NO_INTERNET -> {
                    Utility.displayErrorSnackBar(
                        findViewById<View>(android.R.id.content).rootView,
                        "Không có internet",
                        this
                    )
                }
            }
            EventBus.getDefault().removeStickyEvent(event)
        }
    }
}