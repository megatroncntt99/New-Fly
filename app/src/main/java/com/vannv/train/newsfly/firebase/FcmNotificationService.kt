package com.vannv.train.newsfly.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.utils.LogCat

/**
 * Author: vannv8@fpt.com.vn
 * Date: 11/07/2022
 */
class FcmNotificationService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val notification = message.notification
        LogCat.d(notification.toString())

        val builder = NotificationCompat.Builder(this, "My Notification").apply {
            setContentTitle("My Title")
            setContentText("Bạn vừa nhận được một mã dịch vụ.Bạn vừa nhận được một mã dịch vụ.Bạn vừa nhận được một mã dịch vụ.Bạn vừa nhận được một mã dịch vụ.Bạn vừa nhận được một mã dịch vụ.Bạn vừa nhận được một mã dịch vụ.Bạn vừa nhận được một mã dịch vụ.")
            priority = NotificationCompat.PRIORITY_HIGH
            setSmallIcon(R.drawable.news_fly_logo);
            setAutoCancel(true)
        }
        with(NotificationManagerCompat.from(this)) {
            NotificationChannel(
                "My Notification",
                "My Notification",
                NotificationManager.IMPORTANCE_HIGH
            ).let {
                createNotificationChannel(it)
            }
            notify(1, builder.build())
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        LogCat.d(token)
    }
}