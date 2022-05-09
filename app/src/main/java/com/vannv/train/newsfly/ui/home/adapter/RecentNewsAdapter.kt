package com.vannv.train.newsfly.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vannv.train.newsfly.BR
import com.vannv.train.newsfly.databinding.ItemRecentNewsBinding
import com.vannv.train.newsfly.room.entity.RecentArticle


/**
 * Creator: Nguyen Van Van
 * Date: 21,April,2022
 * Time: 9:04 AM
 */

class RecentNewsAdapter(val listener: (RecentArticle) -> Unit) :
    PagingDataAdapter<RecentArticle, RecentNewsAdapter.ItemViewHolder>(RecentNewsDiffCallback()) {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        ItemRecentNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    inner class ItemViewHolder(private val binding: ItemRecentNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setOnClickItem {
                binding.model?.let(listener)
            }
        }

        fun bind(model: RecentArticle) {
            binding.setVariable(BR.model, model)
            binding.executePendingBindings()
        }
    }
}