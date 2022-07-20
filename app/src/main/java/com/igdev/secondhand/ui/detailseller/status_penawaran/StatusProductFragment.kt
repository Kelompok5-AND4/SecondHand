package com.igdev.secondhand.ui.detailseller.status_penawaran

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentPenawaranBuyerToSellerBinding
import com.igdev.secondhand.databinding.FragmentStatusProductBinding
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.ui.detailseller.TawaranSellerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatusProductFragment(
    id :Int?,
    token:String,
    buyerName : String,
    city : String,
    private val submit: (status:String)-> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentStatusProductBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TawaranSellerViewModel by viewModels()
    private val idProduct= id
    private val tokens= token
    private val name= buyerName
    private val location= city
    var status = "pending"



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatusProductBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvNamaPembeli.text = name
        binding.tvLokasi.text = location

        /*if (binding.radioTerima.isChecked){
            status = "available"
        }else if (binding.radioTolak.isChecked){
            status = "seller"
        }*/
        binding.radioGroup.setOnCheckedChangeListener { compoundButton, b ->
            val id:Int = binding.radioGroup.checkedRadioButtonId
            if (id!=1){
                binding.btnKonfirmasi.isEnabled = true
                binding.btnKonfirmasi.backgroundTintList = requireContext().getColorStateList(R.color.abu_muda)
            }
        }
        Log.d("radio","$status")
        binding.btnKonfirmasi.setOnClickListener {
            when(binding.radioGroup.checkedRadioButtonId){
                R.id.radio_terima->{
                    status="seller"
                }
                R.id.radio_tolak->{
                    status="available"
                }
            }
            submit.invoke(status)
            dismiss()
        }
    }


}