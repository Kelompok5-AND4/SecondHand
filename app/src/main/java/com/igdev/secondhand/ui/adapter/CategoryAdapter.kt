package com.igdev.secondhand.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.BannerItemBinding
import com.igdev.secondhand.databinding.CategoryItemBinding
import com.igdev.secondhand.model.AllProductResponse
import com.igdev.secondhand.model.CategoryResponseItem

class CategoryAdapter(private val onClick: CategoryAdapter.OnClickListener):RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private val diffCallBack = object: DiffUtil.ItemCallback<CategoryResponseItem>(){
        override fun areItemsTheSame(
            oldItem: CategoryResponseItem,
            newItem: CategoryResponseItem
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: CategoryResponseItem,
            newItem: CategoryResponseItem
        ): Boolean {
            return oldItem.name == newItem.name
        }

    }
    private val differ = AsyncListDiffer(this,diffCallBack)
    fun submitData(value:List<CategoryResponseItem>?) = differ.submitList(value)


    interface OnClickListener {
        fun onClickItem (data: CategoryResponseItem)
    }
    inner class ViewHolder(private val binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (data: CategoryResponseItem){
            //https://raw.githubusercontent.com/Kelompok5-AND4/CategoryIconDirectory/master/Electronik.png
            val imageUrl = "https://raw.githubusercontent.com/Kelompok5-AND4/CategoryIconDirectory/master/${data.name}.png"
            binding.tvCategoryName.text = data.name
            Glide.with(binding.root).load(imageUrl).placeholder(R.drawable.default_rv).into(binding.ivCategory)
            binding.root.setOnClickListener {
                onClick.onClickItem(data)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ViewHolder(CategoryItemBinding.inflate(inflate,parent,false))
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