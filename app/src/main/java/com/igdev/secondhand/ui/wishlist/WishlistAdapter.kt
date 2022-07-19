package com.igdev.secondhand.ui.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.igdev.secondhand.databinding.ProductItemBinding
import com.igdev.secondhand.model.wishlist.GetWishlistResponse

class WishlistAdapter(private val onClick: OnClickListener) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<GetWishlistResponse>() {
        override fun areItemsTheSame(oldItem: GetWishlistResponse, newItem: GetWishlistResponse): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: GetWishlistResponse, newItem: GetWishlistResponse): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
    private val differ = AsyncListDiffer(this, diffCallBack)
    fun submitData(value: List<GetWishlistResponse>?) = differ.submitList(value)

    interface OnClickListener{
        fun onCLickItem (data :GetWishlistResponse)
    }
    inner class ViewHolder(private val binding : ProductItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : GetWishlistResponse) {
            binding.apply {
                tvProductName.text = data.product.name
                tvHarga.text = data.product.basePrice.toString()
                Glide.with(binding.root)
                    .load(data.product.imageUrl)
                    .into(ivProduct)
                root.setOnClickListener {
                    onClick.onCLickItem(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ProductItemBinding.inflate(inflater, parent, false))
    }
    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int =  differ.currentList.size

}