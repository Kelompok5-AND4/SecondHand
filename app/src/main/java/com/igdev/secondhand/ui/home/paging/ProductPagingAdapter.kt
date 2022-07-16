package com.igdev.secondhand.ui.home.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.igdev.secondhand.databinding.ProductItemBinding
import com.igdev.secondhand.model.PagingProduct.Data
import com.igdev.secondhand.rupiah

class ProductPagingAdapter : PagingDataAdapter<Data,
        ProductPagingAdapter.ImageViewHolder>(diffCallback) {


    inner class ImageViewHolder(
        val binding: ProductItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currChar = getItem(position)

        holder.binding.apply {

            holder.itemView.apply {
                tvProductName.text = currChar?.name
                tvHarga.text = rupiah(currChar?.basePrice.toString().toInt())
                val imageLink = currChar?.imageUrl
                Glide.with(holder.itemView).load(imageLink).into(ivProduct)
            }
        }

    }


}