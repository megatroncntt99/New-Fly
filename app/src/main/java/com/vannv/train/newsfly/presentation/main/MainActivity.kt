package com.vannv.train.newsfly.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.data.remote.base.TypeError
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
        if (event is TypeError) {
            when (event) {
                TypeError.NO_INTERNET -> {

                }
                TypeError.REDIRECT_RESPONSE_ERROR -> {

                }
                TypeError.CLIENT_REQUEST_ERROR -> {

                }
                TypeError.SERVER_RESPONSE_ERROR -> {

                }
                TypeError.ANOTHER_ERROR -> {

                }
            }
            EventBus.getDefault().removeStickyEvent(event)
        }
    }
}