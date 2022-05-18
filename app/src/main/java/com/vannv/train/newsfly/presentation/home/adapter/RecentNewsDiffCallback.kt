package com.vannv.train.newsfly.presentation.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.vannv.train.newsfly.domain.entity.RecentArticle

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 4:11 PM
 */
class RecentNewsDiffCallback : DiffUtil.ItemCallback<RecentArticle>() {

    override fun areItemsTheSame(oldItem: RecentArticle, newItem: RecentArticle): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: RecentArticle, newItem: RecentArticle): Boolean {
        return oldItem == newItem
    }

}