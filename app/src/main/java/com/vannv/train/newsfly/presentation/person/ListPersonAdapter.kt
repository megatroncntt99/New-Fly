package com.vannv.train.newsfly.presentation.person

import androidx.databinding.ViewDataBinding
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.databinding.ItemPersonBinding
import com.vannv.train.newsfly.domain.entity.New
import com.vannv.train.newsfly.presentation.base.BaseAdapter
import com.vannv.train.newsfly.presentation.base.BaseDiffCallback
import persondb.PersonEntity

/**
 * Author: vannv8@fpt.com.vn
 * Date: 19/07/2022
 */
class ListPersonAdapter(private val onItemClick: (PersonEntity) -> Unit,private val onClickDeleteItem: (PersonEntity) -> Unit) :
    BaseAdapter<PersonEntity, ListPersonAdapter.ItemViewHolder>(PersonDiffCallback()) {

    inner class ItemViewHolder(private val binding: ItemPersonBinding) : BaseAdapter.BaseViewHolder<PersonEntity>(binding) {
        init {
            binding.setOnItemClick {
                binding.model?.let(onItemClick)
            }
            binding.setOnClickDeleteItem {
                binding.model?.let(onClickDeleteItem)
            }
        }
    }

    override fun getLayoutRes(): Int = R.layout.item_person

    override fun getItemViewHolder(viewBinding: ViewDataBinding): ItemViewHolder {
        return when (viewBinding) {
            is ItemPersonBinding -> ItemViewHolder(viewBinding)
            else -> throw IllegalStateException("Unknown viewType $viewBinding")
        }
    }
}

class PersonDiffCallback : BaseDiffCallback<PersonEntity>() {
    override fun areItemsSame(oldItem: PersonEntity, newItem: PersonEntity): Boolean {
        return oldItem.id == newItem.id
    }
}