package com.vannv.train.newsfly.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.vannv.train.newsfly.domain.entity.New

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 4:04 PM
 */

@BindingAdapter("displayImage")
fun ImageView.setDisplayImage(item: New) {
    Glide.with(this)
        .load(item.urlToImage)
        .into(this)
}

@BindingAdapter("displayImage2")
fun ImageView.setDisplayImage2(item: New) {
    Glide.with(this)
        .load(item.urlToImage)
        .into(this)
}
