package com.igdev.secondhand.ui.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.igdev.secondhand.databinding.WishlistItemBinding
import com.igdev.secondhand.model.wishlist.Product

class WishlistAdapter(private val onClick: OnClickListener) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
    private val differ = AsyncListDiffer(this, diffCallBack)
    fun submitData(value: List<Product>?) = differ.submitList(value)

    interface OnClickListener{
        fun onCLickItem (data :Product)
    }
    inner class ViewHolder(private val binding : WishlistItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : Product, position: Int) {
            binding.apply {
                tvMessage.text = data.description
                tvProductName.text = data.name
                tvHarga.text = data.basePrice.toString()
                Glide.with(binding.root)
                    .load(data.imageUrl)
                    .into(ivProduct)
                root.setOnClickListener {
                    onClick.onCLickItem(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(WishlistItemBinding.inflate(inflater, parent, false))
    }
    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data,position)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}