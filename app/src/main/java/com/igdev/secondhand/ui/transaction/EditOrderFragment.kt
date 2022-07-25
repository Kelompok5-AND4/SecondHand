package com.igdev.secondhand.ui.transaction

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.cottacush.android.currencyedittext.CurrencyInputWatcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentEditOrderBinding
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.detail.PostOrderReq
import com.igdev.secondhand.model.detail.PostOrderResponse
import com.igdev.secondhand.model.detail.PutOrderReq
import com.igdev.secondhand.rupiah
import com.igdev.secondhand.ui.MainFragment
import com.igdev.secondhand.ui.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class EditOrderFragment(
    id :Int,
    OrderPrice:String,
    productName : String,
    productPrice : String,
    imageProduct : String,
    private val submit: (PutOrderReq)-> Unit) : BottomSheetDialogFragment(){

    private var _binding : FragmentEditOrderBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private val name = productName
    private val price = productPrice
    private val image = imageProduct
    private val productId = id
    private val priceOrder = OrderPrice
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvNamaProduct.text = name
        binding.tvHarga.text = rupiah(price.toInt())
        binding.etTawar.setText(priceOrder)
        Glide.with(binding.root).load(image).into(binding.ivFotoProduct)
        binding.btnKirim.setOnClickListener {
            if (binding.etTawar.text.isNullOrEmpty()) {
                binding.tilTawar.error = "Input tawar harga tidak boleh kosong"
            }else {
                val edit = binding.etTawar
                val nego = binding.etTawar.getNumericValue().toInt().toString()
                binding.etTawar.addTextChangedListener(CurrencyInputWatcher(edit,"Rp ", Locale.getDefault()))
                val requestEditTawar =
                    PutOrderReq(nego )
                submit.invoke(requestEditTawar)
                dismiss()
            }
        }
        binding.btnUp.setOnClickListener {
            dismiss()
        }
    }

}