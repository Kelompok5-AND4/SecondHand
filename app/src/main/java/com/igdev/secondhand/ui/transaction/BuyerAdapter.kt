package com.igdev.secondhand.ui.transaction

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
import com.igdev.secondhand.model.buyerorder.BuyerOrderResponse
import com.igdev.secondhand.rupiah

class BuyerAdapter(
    private val onClick: (BuyerOrderResponse) -> Unit,
    private val onDelete: (BuyerOrderResponse) -> Unit,
    private val onEdit: (BuyerOrderResponse) -> Unit,
) :
    RecyclerView.Adapter<BuyerAdapter.ViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<BuyerOrderResponse>(){
        override fun areItemsTheSame(
            oldItem: BuyerOrderResponse,
            newItem: BuyerOrderResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BuyerOrderResponse,
            newItem: BuyerOrderResponse
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this,diffCallBack)
    fun submitData(value:List<BuyerOrderResponse>?) = differ.submitList(value)


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
        fun bind(data: BuyerOrderResponse){
            binding.apply {
                when (data.status) {
                    "pending" -> {
                        tvMessage.text = "Dalam Pengajuan"
                    }
                    "declined" -> {
                        tvMessage.text = "Pengajuan Ditolak"
                    }
                    "accepted" -> {
                        tvMessage.text = "Pengajuan Diterima"
                    }
                    else -> {
                        tvMessage.text = "Produk sudah tidak ada"
                    }
                }
                binding.btnEdit.visibility = View.VISIBLE
                binding.btnDelete.visibility = View.VISIBLE
                tvProductName.text = data.productName
                tvHarga.text = rupiah(data.price)
                Glide.with(binding.root)
                    .load(data.product.imageUrl ?: R.drawable.default_rv)
                    .centerCrop()
                    .into(ivProduct)
                root.setOnClickListener{
                    onClick(data)
                }
                btnDelete.setOnClickListener{
                    onDelete(data)
                }
                btnEdit.setOnClickListener{
                    onEdit(data)
                }
            }
        }
    }

}