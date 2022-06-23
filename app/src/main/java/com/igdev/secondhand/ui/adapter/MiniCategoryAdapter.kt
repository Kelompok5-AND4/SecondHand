package com.igdev.secondhand.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.igdev.secondhand.databinding.CategoryItemBinding
import com.igdev.secondhand.databinding.MiniCategoryItemBinding
import com.igdev.secondhand.model.CategoryResponseItem

class MiniCategoryAdapter(private val onClick: OnClickListener):RecyclerView.Adapter<MiniCategoryAdapter.ViewHolder>()  {

    private val diffCallBack = object: DiffUtil.ItemCallback<CategoryResponseItem>(){
        override fun areItemsTheSame(
            oldItem: CategoryResponseItem,
            newItem: CategoryResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
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
    inner class ViewHolder(private val binding: MiniCategoryItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (data: CategoryResponseItem){
            binding.tvNamaKategori.text = data.name
            binding.root.setOnClickListener {
                onClick.onClickItem(data)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiniCategoryAdapter.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ViewHolder(MiniCategoryItemBinding.inflate(inflate,parent,false))
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