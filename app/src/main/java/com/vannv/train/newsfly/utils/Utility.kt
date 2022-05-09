package com.vannv.train.newsfly.utils

import android.content.Context
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.ui.main.MainActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 11:43 AM
 */

object Utility {
    fun formatDate(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.UK)
        val outputFormat = SimpleDateFormat("yyyy/MM/dd ", Locale.UK)
        try {
            return outputFormat.format(inputFormat.parse(date) ?: "")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    fun displayErrorSnackBar(view: View, s: String, context: Context) {
        Snackbar.make(view, s, Snackbar.LENGTH_LONG)
            .withColor(ContextCompat.getColor(context, R.color.red))
            .setTextColor(ContextCompat.getColor(context, R.color.white))
            .show()
    }

    private fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar {
        this.view.setBackgroundColor(colorInt)
        return this
    }
}