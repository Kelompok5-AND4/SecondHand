package com.igdev.secondhand.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.igdev.secondhand.databinding.NotificationItemBinding
import com.igdev.secondhand.model.notification.NotifResponseItem

class NotificationAdapter(private val onItemClick: onClickListener) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<NotifResponseItem>() {
        override fun areItemsTheSame(
            oldItem: NotifResponseItem,
            newItem: NotifResponseItem
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: NotifResponseItem,
            newItem: NotifResponseItem
        ): Boolean = oldItem.hashCode() == newItem.hashCode()

    }
    private val differ = AsyncListDiffer(this@NotificationAdapter, diffCallBack)

    fun submitData(value: List<NotifResponseItem>?) = differ.submitList(value)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(NotificationItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: NotificationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NotifResponseItem) {
            binding.apply {
                tvProductName.text = data.sellerName
                tvPenawaranBerhasil.text = data.status
                tvDateNotif.text = data.transactionDate
                tvHargaDiCoret.setText("${data.bidPrice}").toString()
                tvHargaDiCoret.text = ("<strike> ${data.bidPrice}</strike>")
                Glide.with(root)
                    .load(data.imageUrl)
                    .into(ivProduct)
                root.setOnClickListener {
                    onItemClick.onClickItem(data)
                }
            }

        }
    }


    interface onClickListener {
        fun onClickItem(data: NotifResponseItem)

    }
}


