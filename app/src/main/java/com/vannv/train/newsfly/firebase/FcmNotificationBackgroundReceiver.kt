package com.vannv.train.newsfly.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.utils.LogCat


/**
 * Author: vannv8@fpt.com.vn
 * Date: 11/07/2022
 */
class FcmNotificationBackgroundReceiver : BroadcastReceiver() {
    private val keyTitle: String = "gcm.notification.title"
    private val keyBody: String = "gcm.notification.body"
    private val keyImage: String = "gcm.notification.image"
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

            Glide.with(context)
                .asBitmap()
                .load("${data[keyImage]}")
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        LogCat.d("Get Image Success")
                        val bigPictureStyle = NotificationCompat.BigPictureStyle()
                            .setBigContentTitle("${data[keyTitle]}")
                            .setSummaryText("${data[keyBody]}")
                            .bigPicture(resource)

                        val builder = NotificationCompat.Builder(context, "My Notification").apply {
                            setContentTitle("${data[keyTitle]}")
                            setStyle(bigPictureStyle)
                            setContentText("${data[keyBody]}")
                            priority = NotificationCompat.PRIORITY_HIGH
                            setSmallIcon(R.drawable.news_fly_logo)
                            setAutoCancel(true)
                            setLargeIcon(resource)
                        }
                        with(NotificationManagerCompat.from(context)) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationChannel(
                                    "My Notification",
                                    "My Notification",
                                    NotificationManager.IMPORTANCE_HIGH
                                ).let {
                                    createNotificationChannel(it)
                                }
                            }
                            notify(1, builder.build())
                        }
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        LogCat.d("Get Image Failed")
                        val builder = NotificationCompat.Builder(context, "My Notification").apply {
                            setContentTitle("${data[keyTitle]}")
                            setStyle(NotificationCompat.BigTextStyle().bigText("${data[keyBody]}"))
                            setContentText("${data[keyBody]}")
                            priority = NotificationCompat.PRIORITY_HIGH
                            setSmallIcon(R.drawable.news_fly_logo)
                            setAutoCancel(true)
                        }
                        with(NotificationManagerCompat.from(context)) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationChannel(
                                    "My Notification",
                                    "My Notification",
                                    NotificationManager.IMPORTANCE_HIGH
                                ).let {
                                    createNotificationChannel(it)
                                }
                            }
                            notify(1, builder.build())
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        } catch (e: Exception) {
            LogCat.e("onReceiveNotificationData Error$e")
        }
    }
}
