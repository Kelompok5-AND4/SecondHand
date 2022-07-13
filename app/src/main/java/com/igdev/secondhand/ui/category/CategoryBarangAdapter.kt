package com.igdev.secondhand.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.igdev.secondhand.databinding.ItemBinding
import com.igdev.secondhand.model.AllProductResponse

class CategoryBarangAdapter(private val onClick: OnClickListener) :
    RecyclerView.Adapter<CategoryBarangAdapter.ViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<AllProductResponse>() {
        override fun areItemsTheSame(
            oldItem: AllProductResponse,
            newItem: AllProductResponse
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: AllProductResponse,
            newItem: AllProductResponse
        ): Boolean {
            return oldItem.name == newItem.name
        }

    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    fun submitData(value: List<AllProductResponse?>) = differ.submitList(value)

    interface OnClickListener {
        fun onClickItem(data: AllProductResponse)
    }

    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AllProductResponse) {
            binding.apply {
                tvTittle.text = data.name
                tvHarga.text = data.basePrice.toString()
                Glide.with(binding.root)
                    .load(data.imageUrl)
                    .into(ivImage)
                binding.root.setOnClickListener {
                    onClick.onClickItem(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ViewHolder(ItemBinding.inflate(inflate, parent, false))
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }
}
