package com.vannv.train.newsfly.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vannv.train.newsfly.databinding.ItemPopularNewsBinding
import com.vannv.train.newsfly.databinding.ItemRecentNewsBinding
import com.vannv.train.newsfly.room.entity.PopularArticle
import com.vannv.train.newsfly.room.entity.RecentArticle
import com.vannv.train.newsfly.ui.home.adapter.PopularNewsAdapter
import com.vannv.train.newsfly.ui.home.adapter.PopularNewsDiffCallback
import com.vannv.train.newsfly.ui.home.adapter.RecentNewsDiffCallback

/**
 * Creator: Nguyen Van Van
 * Date: 09,May,2022
 * Time: 10:23 AM
 */

class SearchListAdapter(val listener: (RecentArticle) -> Unit) :
    ListAdapter<RecentArticle, SearchListAdapter.ItemViewHolder>(RecentNewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            ItemRecentNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: ItemRecentNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setOnClickItem {
                binding.setOnClickItem {
                    binding.model?.let(listener)
                }
            }
        }

        fun bind(item: RecentArticle) {
            binding.model = item
            binding.executePendingBindings()
        }
    }

}