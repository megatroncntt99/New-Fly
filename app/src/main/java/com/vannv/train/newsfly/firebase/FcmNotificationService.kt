package com.vannv.train.newsfly.firebase

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.icu.text.CaseMap
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.presentation.main.MainActivity
import com.vannv.train.newsfly.utils.LogCat
import retrofit2.http.Body

/**
 * Author: vannv8@fpt.com.vn
 * Date: 11/07/2022
 */
class FcmNotificationService : FirebaseMessagingService() {

    private val keyTitle: String = "title"
    private val keyBody: String = "body"
    private val keyImage: String = "image"

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val data = message.data
        Glide.with(this)
            .asBitmap()
            .load(data[keyImage])
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    LogCat.d("Get Image Success")
                    val bigPictureStyle = NotificationCompat.BigPictureStyle()
                        .setBigContentTitle(data[keyTitle])
                        .setSummaryText(data[keyBody])
                        .bigPicture(resource)
                        .bigLargeIcon(BitmapFactory.decodeResource(this@FcmNotificationService.resources, R.drawable.news_fly_logo))
                    createNotification(bigPictureStyle, data[keyTitle].orEmpty(), data[keyBody].orEmpty())
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    LogCat.d("Get Image Failed")
                    createNotification(NotificationCompat.BigTextStyle().bigText(data[keyBody]), data[keyTitle].orEmpty(), data[keyBody].orEmpty())
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }

    private fun createNotification(style: NotificationCompat.Style, title: String, body: String) {
        val pendingIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            val bd = Bundle().apply {
                putString("Title", title)
            }
            putExtras(bd)
        }.mapToPendingIntent(this, 1)
        val builder = NotificationCompat.Builder(this@FcmNotificationService, "My Notification").apply {
            setContentTitle(title)
            setStyle(style)
            setContentText(body)
            priority = NotificationCompat.PRIORITY_HIGH
            setSmallIcon(R.drawable.news_fly_logo)
            setAutoCancel(true)
            setContentIntent(pendingIntent)
        }
        with(NotificationManagerCompat.from(this@FcmNotificationService)) {
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

    @SuppressLint("UnspecifiedImmutableFlag", "InlinedApi")
    private fun Intent.mapToPendingIntent(context: Context, notificationId: Int): PendingIntent {
        return PendingIntent.getActivity(
            context,
            notificationId,
            this,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        LogCat.d(token)
    }
}