package com.igdev.secondhand.ui.transaction

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.igdev.secondhand.databinding.BuyerHistoryItemBinding
import com.igdev.secondhand.databinding.ProductItemBinding
import com.igdev.secondhand.model.notification.NotifResponseItem

class BuyerAdapter(
    private val onItemClick : OnClickListener
) :
    RecyclerView.Adapter<BuyerAdapter.ViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<NotifResponseItem>(){
        override fun areItemsTheSame(
            oldItem: NotifResponseItem,
            newItem: NotifResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NotifResponseItem,
            newItem: NotifResponseItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this,diffCallBack)
    fun submitData(value:List<NotifResponseItem>?) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(BuyerHistoryItemBinding.inflate(inflater,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: BuyerHistoryItemBinding):
        RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(data: NotifResponseItem){
            binding.apply {
                when (data.status) {
                    "bid" -> {
                        if (data.product != null){
                            if(data.receiverId == data.product.userId){
                                tvMessage.text = "Dalam Pengajuan"
                            } else {
                                tvMessage.text = "Dalam Pengajuan"
                            }
                        } else {
                            tvMessage.text = "Produk Sudah ada yang beli"
                        }
                    }
                    "declined" -> {
                        if (data.product != null){
                            if (data.receiverId == data.product.userId){
                                tvMessage.text = "Pengajuan Ditolak"
                            } else {
                                tvMessage.text = "Pengajuan Ditolak"
                            }
                        } else {
                            tvMessage.text = "Produk Sudah ada yang beli"
                        }
                    }
                    "accepted" -> {
                        if (data.product != null){
                            if (data.receiverId == data.product.userId){
                                tvMessage.text = "Pengajuan Diterima"
                            } else {
                                tvMessage.text = "Pengajuan Diterima"
                            }
                        } else {
                            tvMessage.text = "Produk Sudah ada yang beli"
                        }
                    }
                    else -> {
                        tvMessage.text = "Dalam Pengajuan"
                    }
                }
                tvProductName.text = data.productName
                tvHarga.text = data.bidPrice.toString()
                if (!data.read){
                    Glide.with(binding.root)
                        .load(data.imageUrl)
                        .centerCrop()
                        .into(ivProduct)
                }
                root.setOnClickListener{
                    onItemClick.onClickItem(data)
                }
            }
        }
    }

    interface OnClickListener{
        fun onClickItem(data: NotifResponseItem)
    }
}