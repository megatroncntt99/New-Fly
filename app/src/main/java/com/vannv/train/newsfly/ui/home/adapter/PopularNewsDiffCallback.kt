package com.vannv.train.newsfly.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.vannv.train.newsfly.room.entity.PopularArticle

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 4:10 PM
 */

class PopularNewsDiffCallback : DiffUtil.ItemCallback<PopularArticle>() {

    override fun areItemsTheSame(oldItem: PopularArticle, newItem: PopularArticle): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PopularArticle, newItem: PopularArticle): Boolean {
        return oldItem == newItem
    }

}