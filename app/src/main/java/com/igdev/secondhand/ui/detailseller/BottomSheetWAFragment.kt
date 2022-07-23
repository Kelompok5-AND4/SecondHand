package com.igdev.secondhand.ui.detailseller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentBottomSheetWABinding
import com.igdev.secondhand.databinding.FragmentTawaranSellerBinding
import com.igdev.secondhand.model.sellerorder.SellerOrderResponseItem
import com.igdev.secondhand.rupiah
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetWAFragment(
    buyerName : String,
    city : String,
    phone :String,
    basePrice :Int,
    bid:Int,
    product:String,
    private val submit: ()-> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetWABinding? = null
    private val binding get() = _binding!!
    val buyer = buyerName
    val location = city
    val phoneNum =phone
    val base = basePrice
    val bidPrice = bid
    val productName = product
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetWABinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvCity.text = location
            tvDitawar.text = rupiah(bidPrice)
            tvPrice.text = rupiah(base)
            tvNameProduct.text = productName
            tvName.text = buyer
            btnKirim.setOnClickListener {
                submit.invoke()
                dismiss()
            }
        }
    }

}