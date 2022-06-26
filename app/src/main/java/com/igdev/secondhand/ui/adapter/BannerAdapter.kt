package com.igdev.secondhand.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.BannerItemBinding

class BannerAdapter(private val imageList : ArrayList<Int>, private val viewPager2: ViewPager2)
    :RecyclerView.Adapter<BannerAdapter.ImageViewHolder>(){

    class ImageViewHolder(val binding : BannerItemBinding) : RecyclerView.ViewHolder(binding.root){
        val imageView : ImageView = itemView.findViewById(R.id.ivBanner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImageViewHolder(BannerItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position])
        if (position == imageList.size-1){
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }
}