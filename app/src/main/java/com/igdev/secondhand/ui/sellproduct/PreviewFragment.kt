package com.igdev.secondhand.ui.sellproduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentPreviewBinding
import com.igdev.secondhand.databinding.FragmentSellBinding
import com.igdev.secondhand.model.local.ProductPreview
import com.igdev.secondhand.model.local.UserLogin
import com.igdev.secondhand.ui.viewmodel.SellViewModel

class PreviewFragment : Fragment() {
    private var _binding: FragmentPreviewBinding?=null
    private val binding get() = _binding!!

    private val args:PreviewFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentPreviewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(binding.root).load(args.product?.productImage).into(binding.fotoProduk)
        binding.tvDeskripsi.text = args.product?.productDescriptionn
        binding.tvHarga.text = args.product?.productPrice
        binding.tvKategori.text = args.product?.productCategory
        binding.tvNamaProduct.text = args.product?.productName
    }
}