package com.igdev.secondhand.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.igdev.secondhand.databinding.DetailCategoryItemBinding
import com.igdev.secondhand.model.detail.Category

class DetailCategoryAdapter(private val onClick: OnClickListener) : RecyclerView.Adapter<DetailCategoryAdapter.ViewHolder>() {

    private val diffCallBack = object: DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean {
            return oldItem.name == newItem.name
        }

    }
    private val differ = AsyncListDiffer(this,diffCallBack)
    fun submitData(value:List<Category>?) = differ.submitList(value)
    interface OnClickListener {
        fun onClickItem (data: Category)
    }
    inner class ViewHolder(private val binding: DetailCategoryItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (data: Category){
            binding.tvNamaKategori.text = data.name
            binding.root.setOnClickListener {
                onClick.onClickItem(data)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailCategoryAdapter.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ViewHolder(DetailCategoryItemBinding.inflate(inflate,parent,false))
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