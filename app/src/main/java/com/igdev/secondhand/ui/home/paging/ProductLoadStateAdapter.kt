package com.igdev.secondhand.ui.home.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.LoadingItemBinding

class ProductLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ProductLoadStateAdapter.ReposLoadStateViewHolder>() {

    inner class ReposLoadStateViewHolder(
        private val binding: LoadingItemBinding,
        retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }
    }

    override fun onBindViewHolder(holder: ReposLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ReposLoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.loading_item, parent, false)
        val binding = LoadingItemBinding.bind(view)
        return ReposLoadStateViewHolder(binding, retry)
    }
}