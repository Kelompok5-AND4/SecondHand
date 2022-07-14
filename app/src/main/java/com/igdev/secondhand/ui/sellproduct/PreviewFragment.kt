package com.igdev.secondhand.ui.sellproduct

import android.app.AlertDialog
import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentPreviewBinding
import com.igdev.secondhand.databinding.FragmentSellBinding
import com.igdev.secondhand.listCategoryId
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.local.ProductPreview
import com.igdev.secondhand.model.local.UserLogin
import com.igdev.secondhand.rupiah
import com.igdev.secondhand.ui.MainFragment
import com.igdev.secondhand.ui.viewmodel.SellViewModel
import com.igdev.secondhand.uriToFile
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewFragment : Fragment() {
    private var _binding: FragmentPreviewBinding?=null
    private val binding get() = _binding!!
    private val viewModel:SellViewModel by viewModels()

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

        MainFragment.currentPage = R.id.transactionFragment

        val productImage = args.product?.productImage
        val productDescription =args.product?.productDescription
        val productPrice =args.product?.productPrice
        val productCategory =args.product?.productCategory
        val productName =args.product?.productName
        val userName =args.userdata?.username
        val address =args.userdata?.address
        val userImage =args.userdata?.image
        val token =args.userdata?.token


        Glide.with(binding.root).load(productImage).into(binding.fotoProduk)
        Glide.with(binding.root).load(userImage).into(binding.ivFotoProfile)
        binding.tvDeskripsi.text = productDescription
        binding.tvHarga.text = rupiah(productPrice.toString().toInt())
        binding.tvCategory.text = productCategory
        binding.tvNamaProduct.text = productName
        binding.tvNamaPenjual.text = userName
        binding.tvLokasi.text = address

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnTerapkan.setOnClickListener {
            if(token != null){
                viewModel.postProduct(
                    token,
                    productName.toString(),
                    productDescription.toString(),
                    productPrice.toString(),
                    listCategoryId,
                    address.toString(),
                    uriToFile(Uri.parse(productImage),requireContext())
                )
            }
        }
        val progressDialog = ProgressDialog(requireContext())
        viewModel.postProduct.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    progressDialog.dismiss()
                    Toast.makeText(requireContext(), "Barang Anda berhasil di terbitkan", Toast.LENGTH_SHORT)
                        .show()
                    listCategoryId.clear()
                    findNavController().navigate(R.id.action_previewFragment_to_mainFragment)
                }
                Status.ERROR -> {
                    progressDialog.dismiss()
                    var message = ""
                    when (it.message) {
                        "HTTP 400 Bad Request" -> {
                            message = "${it.message}"
                        }
                    }
                    AlertDialog.Builder(requireContext())
                        .setTitle("Pesan")
                        .setMessage(message)
                        .setPositiveButton("Iya") { positiveButton, _ ->
                            positiveButton.dismiss()
                        }
                        .show()
                }
                Status.LOADING -> {
                    progressDialog.show()
                }
            }
        }
    }
}