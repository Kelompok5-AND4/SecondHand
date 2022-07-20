package com.igdev.secondhand.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.CategoryItemBinding
import com.igdev.secondhand.databinding.MiniCategoryItemBinding
import com.igdev.secondhand.model.CategoryResponseItem

class MiniCategoryAdapter(private val onClick: OnClickListener):RecyclerView.Adapter<MiniCategoryAdapter.ViewHolder>()  {
    var index =0
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
        fun onClickItem (data: CategoryResponseItem,position: Int)
    }
    inner class ViewHolder(val binding: MiniCategoryItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (data: CategoryResponseItem, position: Int){
            binding.tvNamaKategori.text = data.name
            binding.root.setOnClickListener {
                onClick.onClickItem(data,position)
                index = position
                notifyDataSetChanged()
            }
            if (position == 0) {
                binding.semuaBarang.visibility = View.VISIBLE
                binding.category.visibility = View.GONE
            }else {
                binding.semuaBarang.visibility = View.GONE
                binding.category.visibility = View.VISIBLE
            }
            setupColor(position)
        }

        private fun setupColor(position: Int) {
            if (index==position){
                binding.itemRoot.setBackgroundColor(Color.parseColor("#0064D9"))
            }
            else if(index!=position){
                binding.itemRoot.setBackgroundColor(Color.parseColor("#0094D9"))
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
            holder.bind(data,position)
        }
    }
}