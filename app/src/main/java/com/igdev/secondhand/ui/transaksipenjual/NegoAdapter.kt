package com.igdev.secondhand.ui.transaksipenjual

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
import com.igdev.secondhand.model.sellerorder.SellerOrderResponseItem
import com.igdev.secondhand.rupiah


class NegoAdapter(
    private val onItemClick : OnClickListener
) :
    RecyclerView.Adapter<NegoAdapter.ViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<SellerOrderResponseItem>(){
        override fun areItemsTheSame(
            oldItem: SellerOrderResponseItem,
            newItem: SellerOrderResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SellerOrderResponseItem,
            newItem: SellerOrderResponseItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this,diffCallBack)
    fun submitData(value:List<SellerOrderResponseItem>?) = differ.submitList(value)


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
        fun bind(data: SellerOrderResponseItem){
            binding.apply {
                when(data.status){
                    "accepted"->{
                        tvMessage.text = "accepted"
                    }
                    "pending"->{
                        tvMessage.text = "pending"
                    }
                }
                tvProductName.text = data.productName
                tvHarga.text = rupiah(data.basePrice.toString().toInt())
                Glide.with(binding.root)
                    .load(data.product.imageUrl ?: R.drawable.default_rv)
                    .centerCrop()
                    .into(ivProduct)
                root.setOnClickListener{
                    onItemClick.onClickItem(data)
                }

            }
        }
    }

    interface OnClickListener{
        fun onClickItem(data: SellerOrderResponseItem)
    }
}