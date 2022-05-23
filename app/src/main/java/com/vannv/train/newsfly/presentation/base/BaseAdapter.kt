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

/**
 * Author: vannv8@fpt.com.vn
 * Date: 18/05/2022
 */

abstract class BaseAdapter<IVH : BaseAdapter.BaseViewHolder<T>, T : Any>(
    var diffCallback: BaseDiffCallback<T>
) : ListAdapter<T, IVH>(diffCallback) {

    abstract fun getLayoutRes(): Int

    abstract fun getItemViewHolder(viewBinding: ViewDataBinding): IVH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IVH {
        val viewBinding: ViewDataBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                getLayoutRes(),
                parent,
                false
            )
        return getItemViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: IVH, position: Int) {
        holder.bind(getItem(position))
        setFadeAnimation(holder.itemView)
    }

    abstract class BaseViewHolder<Item>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        open fun bind(model: Item) {
            binding.setVariable(BR.model, model)
            binding.executePendingBindings()
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

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = FADE_DURATION.toLong()
        view.startAnimation(anim)
    }

    companion object {
        const val FADE_DURATION = 500
    }

}







