package com.igdev.secondhand.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.BuyerHistoryItemBinding
import com.igdev.secondhand.databinding.RvLelangBinding
import com.igdev.secondhand.model.sellerproduct.SellerProductResponseItem
import com.igdev.secondhand.rupiah

class LelangAdapter(
    private val onItemClick : OnClickListener
) :
    RecyclerView.Adapter<LelangAdapter.ViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<SellerProductResponseItem>(){
        override fun areItemsTheSame(
            oldItem: SellerProductResponseItem,
            newItem: SellerProductResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SellerProductResponseItem,
            newItem: SellerProductResponseItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this,diffCallBack)
    fun submitData(value:List<SellerProductResponseItem>?) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(RvLelangBinding.inflate(inflater,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: RvLelangBinding):
        RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(data: SellerProductResponseItem){
            binding.apply {
                tvNamaProduct.text = data.name
                tvHarga.text = rupiah(data.basePrice.toString().toInt())
                Glide.with(binding.root)
                    .load(data.imageUrl ?: R.drawable.default_rv)
                    .centerCrop()
                    .into(ivPoster)
                root.setOnClickListener{
                    onItemClick.onClickItem(data)
                }
            }
        }
    }

    interface OnClickListener{
        fun onClickItem(data: SellerProductResponseItem)
    }
}