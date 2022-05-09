package com.vannv.train.newsfly.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.databinding.LoadStateFooterBinding

/**
 * Creator: Nguyen Van Van
 * Date: 21,April,2022
 * Time: 10:15 AM
 */

class AllNewsLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<AllNewsLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(private val binding: LoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener {
                retry()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState is LoadState.Error
                textViewError.isVisible = loadState is LoadState.Error

                if (loadState is LoadState.Error) {
                    textViewError.text = loadState.error.localizedMessage
                        ?: binding.root.context.getString(R.string.unknown_error_occurred)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding =
            LoadStateFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }
}