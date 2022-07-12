package com.vannv.train.newsfly

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.vannv.train.newsfly.firebase.LifecycleUtils
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 11:14 AM
 */

@HiltAndroidApp
class AppNew : Application() {
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

            }

            override fun onActivityStarted(activity: Activity) {
                LifecycleUtils.isAppBackground = false
            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {
                LifecycleUtils.isAppBackground = true
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }

        })
    }
}