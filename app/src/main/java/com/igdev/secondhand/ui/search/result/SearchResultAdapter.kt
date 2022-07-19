package com.igdev.secondhand.ui.search.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.igdev.secondhand.databinding.ProductItemBinding
import com.igdev.secondhand.model.AllProductResponse
import com.igdev.secondhand.rupiah

class SearchResultAdapter(private val onClick: OnClickListener): RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    private val diffCallBack = object: DiffUtil.ItemCallback<AllProductResponse>(){
        override fun areItemsTheSame(
            oldItem: AllProductResponse,
            newItem: AllProductResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AllProductResponse,
            newItem: AllProductResponse
        ): Boolean {
            return oldItem.name == newItem.name
        }

    }
    private val differ = AsyncListDiffer(this,diffCallBack)
    fun submitData(value:List<AllProductResponse>?) = differ.submitList(value)


    interface OnClickListener {
        fun onClickItem (data: AllProductResponse)
    }
    inner class ViewHolder(private val binding: ProductItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (data: AllProductResponse){
            Glide.with(binding.root).load(data.imageUrl).into(binding.ivProduct)
            binding.tvProductName.text = data.name
            binding.ivWishlist.visibility = View.GONE
            binding.tvHarga.text = rupiah(data.basePrice)
            binding.root.setOnClickListener {
                onClick.onClickItem(data)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultAdapter.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ViewHolder(ProductItemBinding.inflate(inflate,parent,false))
    }

    override fun onBindViewHolder(holder: SearchResultAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}