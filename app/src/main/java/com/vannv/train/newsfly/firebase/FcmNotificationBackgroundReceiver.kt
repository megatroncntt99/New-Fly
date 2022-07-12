package com.vannv.train.newsfly.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.legacy.content.WakefulBroadcastReceiver
import com.google.gson.GsonBuilder
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.utils.LogCat

/**
 * Author: vannv8@fpt.com.vn
 * Date: 11/07/2022
 */
@Suppress("DEPRECATION")
class FcmNotificationBackgroundReceiver : WakefulBroadcastReceiver() {
    private val keySerializer: String = "serializer"
    override fun onReceive(context: Context, intent: Intent) {
        // App is in Foreground => return
        LogCat.e("${LifecycleUtils.isAppBackground}}")
        if (!LifecycleUtils.isAppBackground) {
            return
        }

        // App is killed
        try {
            val data: Bundle = intent.extras ?: return

            data.keySet().forEach { key ->
                LogCat.d("$key => ${data[key]}")
            }
            val builder = NotificationCompat.Builder(context, "My Notification").apply {
                setContentTitle("My Title")
                setStyle(
                    NotificationCompat.BigPictureStyle()
                        .setBigContentTitle("Bạn vừa nhận được một mã dịch vụ.Bạn vừa nhận được một mã dịch vụ.Bạn vừa nhận được một mã dịch vụ.Bạn vừa nhận được một mã dịch vụ.Bạn vừa nhận được một mã dịch vụ.Bạn vừa nhận được một mã dịch vụ.Bạn vừa nhận được một mã dịch vụ.")
                        .bigPicture(null)
                )
                setContentText("Bạn vừa nhận được một mã dịch vụ.Bạn vừa nhận được một mã dịch vụ.Bạn vừa nhận được một mã dịch vụ.Bạn vừa nhận được một mã dịch vụ.Bạn vừa nhận được một mã dịch vụ.Bạn vừa nhận được một mã dịch vụ.Bạn vừa nhận được một mã dịch vụ.")
                priority = NotificationCompat.PRIORITY_HIGH
                setSmallIcon(R.drawable.news_fly_logo);
                setAutoCancel(true)
            }
            with(NotificationManagerCompat.from(context)) {
                NotificationChannel(
                    "My Notification",
                    "My Notification",
                    NotificationManager.IMPORTANCE_HIGH
                ).let {
                    createNotificationChannel(it)
                }
                notify(1, builder.build())
            }
        } catch (e: Exception) {
            LogCat.e("onReceiveNotificationData Error$e")
        }
    }
}