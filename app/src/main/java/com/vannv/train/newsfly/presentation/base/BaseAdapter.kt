package com.vannv.train.newsfly.presentation.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vannv.train.newsfly.BR
import com.vannv.train.newsfly.databinding.XLayoutLoadMoreBinding
import com.vannv.train.newsfly.databinding.XLayoutLoadingBinding
import java.lang.NumberFormatException

/**
 * Author: vannv8@fpt.com.vn
 * Date: 18/05/2022
 */

abstract class BaseAdapter<T : Any, IVH : BaseAdapter.BaseViewHolder<T>>(
    diffCallback: BaseDiffCallback<T>
) : ListAdapter<T, BaseAdapter.BaseViewHolder<T>>(diffCallback) {

    private var showLoadingMore = false

    abstract fun getLayoutRes(): Int

    abstract fun getItemViewHolder(viewBinding: ViewDataBinding): IVH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val viewBinding: ViewDataBinding =
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        getLayoutRes(),
                        parent,
                        false
                    )
                return getItemViewHolder(viewBinding)
            }
            VIEW_TYPE_LOADING_MORE -> getLoadMoreViewHolder(parent)
            else -> throw NumberFormatException("No Same View Type $viewType")
        }


    }

    open fun getLoadMoreViewHolder(parent: ViewGroup): LoadMoreViewHolder {
        return LoadMoreViewHolder(
            XLayoutLoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        when (holder) {
            is BaseAdapter<*, *>.LoadMoreViewHolder -> {}
            else -> {
                holder.bind(getItem(position))
            }
        }
        setFadeAnimation(holder.itemView)
    }

    abstract class BaseViewHolder<Item>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        open fun bind(model: Item) {
            binding.setVariable(BR.model, model)
            binding.executePendingBindings()
        }
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = FADE_DURATION.toLong()
        view.startAnimation(anim)
    }

    inner class LoadMoreViewHolder(binding: XLayoutLoadMoreBinding) : BaseViewHolder<T>(binding) {
        override fun bind(model: T) {
        }
    }

    override fun getItemCount(): Int {
        var itemCount: Int = currentList.size
        if (showLoadingMore) itemCount++
        return itemCount
    }

    override fun getItemViewType(position: Int): Int {
        if (showLoadingMore && isPositionFooter(position)) return VIEW_TYPE_LOADING_MORE
        return VIEW_TYPE_ITEM
    }

    fun isPositionFooter(position: Int): Boolean {
        return position == itemCount - 1 && showLoadingMore
    }

    fun setWithShowLoadMore(value: Boolean) {
        showLoadingMore = value
        notifyItemChanged(itemCount - 1, itemCount)
    }

    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING_MORE = 1
        const val FADE_DURATION = 500
    }

}

abstract class BaseDiffCallback<U : Any> :
    DiffUtil.ItemCallback<U>() {
    protected abstract fun areItemsSame(oldItem: U, newItem: U): Boolean

    override fun areItemsTheSame(oldItem: U, newItem: U): Boolean {
        return areItemsSame(oldItem, newItem)
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: U, newItem: U): Boolean {
        return oldItem == newItem
    }
}







