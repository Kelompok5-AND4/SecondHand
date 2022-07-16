package com.igdev.secondhand.ui.detailproduct

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
import com.igdev.secondhand.databinding.CategoryBottomSheetBinding
import com.igdev.secondhand.databinding.FragmentInputPenawaranBinding
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.detail.PostOrderReq
import com.igdev.secondhand.ui.MainFragment
import com.igdev.secondhand.ui.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class InputPenawaranFragment(
    id :Int,
    productName : String,
    productPrice : String,
    imageProduct : String,
    private val submit: ()-> Unit) : BottomSheetDialogFragment(){

    private var _binding : FragmentInputPenawaranBinding? = null
    private val binding get() = _binding!!
    private val viewModel:DetailViewModel by viewModels()
    private val name = productName
    private val price = productPrice
    private val image = imageProduct
    private val productId = id
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInputPenawaranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please Wait...")
        binding.tvNamaProduct.text = name
        binding.tvHarga.text = price
        Glide.with(binding.root).load(image).into(binding.ivFotoProduct)
        binding.btnKirim.setOnClickListener {
            progressDialog.show()
            if (binding.etTawar.text.isNullOrEmpty()) {
                binding.tilTawar.error = "Input tawar harga tidak boleh kosong"
            }else {
                viewModel.getToken()
            }
        }
        viewModel.getToken.observe(viewLifecycleOwner) {
            val edit = binding.etTawar
            val nego = binding.etTawar.getNumericValue().toInt().toString()
            binding.etTawar.addTextChangedListener(CurrencyInputWatcher(edit,"Rp ", Locale.getDefault()))
            val requestHargaTawar =
                PostOrderReq(productId.toString(),nego )
            viewModel.postOrder(it.token, requestHargaTawar)
            Handler().postDelayed({
                progressDialog.dismiss()
                submit.invoke()
                dismiss()
            }, 1000)
        }
        binding.btnUp.setOnClickListener {
            dismiss()
        }
        viewModel.postOrder.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING ->{
                    progressDialog.show()
                }
                Status.SUCCESS -> {
                    progressDialog.dismiss()
                    when (it.data?.code()) {
                        201 -> {
                            Toast.makeText(requireContext(), "Berhasil mengajukan penawaran", Toast.LENGTH_SHORT)
                                .show()
                            MainFragment.currentPage = R.id.homeFragment
                            findNavController().navigate(R.id.action_detailProductFragment_to_penawaranTerkirimFragment)
                            }
                        400 -> {
                            Toast.makeText(
                                context,
                                "Produk sudah terlalu banyak yang menawar",
                                Toast.LENGTH_SHORT
                            ).show()
                            }
                    }
                    Handler().postDelayed({
                        dismiss()
                    }, 1500)
                }
                Status.ERROR ->{
                    progressDialog.dismiss()
                    AlertDialog.Builder(requireContext())
                        .setMessage(it.message)
                        .setPositiveButton("Ok") { dialog, _ ->
                            dialog.dismiss()
                            findNavController().popBackStack()
                        }
                        .show()
                }
            }
        }
    }

}