package com.vannv.train.newsfly.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.databinding.ItemRecentNewsBinding
import com.vannv.train.newsfly.domain.entity.New
import com.vannv.train.newsfly.presentation.base.BaseAdapter

/**
 * Author: vannv8@fpt.com.vn
 * Date: 23/05/2022
 */

class NewsAdapter(val listener: (New) -> Unit) :
    BaseAdapter<NewsAdapter.ItemViewHolder, New>(diffCallback = NewDiffCallback()) {

    inner class ItemViewHolder(private val binding: ItemRecentNewsBinding) :
        BaseAdapter.BaseViewHolder<New>(binding) {
        init {
            binding.setOnClickItem {
                binding.setOnClickItem {
                    binding.model?.let(listener)
                }
            }
        }
    }

    override fun getLayoutRes(): Int = R.layout.item_recent_news

    override fun getItemViewHolder(viewBinding: ViewDataBinding): ItemViewHolder {
        return when (viewBinding) {
            is ItemRecentNewsBinding -> ItemViewHolder(viewBinding)
            else -> throw IllegalStateException("Unknown viewType $viewBinding")
        }
    }

    class NewDiffCallback : BaseDiffCallback<New>() {
        override fun areItemsSame(oldItem: New, newItem: New): Boolean {
            return oldItem.url == newItem.url
        }
    }
}


