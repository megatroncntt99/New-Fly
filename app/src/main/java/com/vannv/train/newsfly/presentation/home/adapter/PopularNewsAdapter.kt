package com.vannv.train.newsfly.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vannv.train.newsfly.databinding.ItemPopularNewsBinding
import com.vannv.train.newsfly.domain.entity.PopularArticle


/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 4:10 PM
 */

class PopularNewsAdapter(val listener: (PopularArticle) -> Unit) :
    ListAdapter<PopularArticle, PopularNewsAdapter.ItemViewHolder>(PopularNewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            ItemPopularNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: ItemPopularNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setOnClickItem {
                binding.setOnClickItem {
                    binding.model?.let(listener)
                }
            }
        }

        fun bind(item: PopularArticle) {
            binding.model = item
            binding.executePendingBindings()
        }
    }

}