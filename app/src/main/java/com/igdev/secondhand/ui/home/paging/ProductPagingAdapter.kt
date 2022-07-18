package com.igdev.secondhand.ui.home.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.ProductItemBinding
import com.igdev.secondhand.model.PagingProduct.Product
import com.igdev.secondhand.rupiah
import com.igdev.secondhand.ui.home.UiModel
import java.text.DecimalFormat
import java.text.NumberFormat

class ProductPagingAdapter(private val onClick: (Product) -> Unit) :
    PagingDataAdapter<UiModel.ProductItem, RecyclerView.ViewHolder>(UIMODEL_COMPARATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val uiModel = getItem(position)) {
            is UiModel.ProductItem -> (holder as RepoViewHolder).bind(uiModel.products)
            else -> {}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ProductItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return RepoViewHolder(binding)
    }

    inner class RepoViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var product: Product? = null

        fun bind(productResponseItem: Product) {

            this.product = productResponseItem

            binding.root.setOnClickListener {
                onClick(productResponseItem)
            }
            Glide.with(binding.root).load(productResponseItem.imageUrl)
                .placeholder(R.drawable.default_rv)
                .error(R.drawable.default_rv)
                .into(binding.ivProduct)
            binding.tvProductName.text = productResponseItem.name
            binding.tvHarga.text = rupiah(productResponseItem.basePrice)
        }
    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<UiModel.ProductItem>() {
            override fun areItemsTheSame(oldItem: UiModel.ProductItem, newItem: UiModel.ProductItem): Boolean {
                return (oldItem.products.name == newItem.products.name)
            }

            override fun areContentsTheSame(oldItem: UiModel.ProductItem, newItem: UiModel.ProductItem): Boolean =
                oldItem == newItem
        }
    }
}