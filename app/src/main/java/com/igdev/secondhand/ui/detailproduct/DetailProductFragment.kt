package com.igdev.secondhand.ui.detailproduct

import android.app.AlertDialog
import android.app.ProgressDialog
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
import com.google.android.material.snackbar.Snackbar
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentDetailProductBinding
import com.igdev.secondhand.databinding.FragmentHomeBinding
import com.igdev.secondhand.model.CategoryResponseItem
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.buyerorder.BuyerOrderResponse
import com.igdev.secondhand.model.detail.Category
import com.igdev.secondhand.ui.adapter.DetailCategoryAdapter
import com.igdev.secondhand.ui.transaction.BuyerAdapter
import com.igdev.secondhand.ui.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList


@AndroidEntryPoint
class DetailProductFragment : Fragment() {
    private var _binding: FragmentDetailProductBinding? = null
    private val binding get() = _binding!!
    private val args: DetailProductFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private var pending = false
    private var accepted = false
    private var decline = false
    private lateinit var categoryAdapter : DetailCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        val progressDialog = ProgressDialog(requireContext())
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailProductFragment_to_mainFragment)
        }
        binding.btnMenu.setOnClickListener {

        }

        viewModel.getToken()
        viewModel.getToken.observe(viewLifecycleOwner) {
            viewModel.getBuyerHistory(it.token)
        }
        viewModel.getBuyerHistory.observe(viewLifecycleOwner) {
            for (data in 0 until (it.data?.size ?: 0)) {
                if (it.data?.get(data)?.productId == args.id && it.data.get(data).status == "pending") {
                    pending = true

                }
            }
            for (data in 0 until (it.data?.size ?: 0)) {
                if (it.data?.get(data)?.productId == args.id && it.data.get(data).status == "accepted") {
                    accepted = true

                }
            }
            for (data in 0 until (it.data?.size ?: 0)) {
                if (it.data?.get(data)?.productId == args.id && it.data.get(data).status == "declined") {
                    decline = true

                }
            }
            if (accepted) {
                binding.btnNego.setText(R.string.diterima_text)
                binding.btnNego.isClickable = false
                binding.btnNego.setBackgroundColor(R.color.hijau)
            } else if (pending) {
                binding.btnNego.setText(R.string.menunggu_text)
                binding.btnNego.isClickable = false
                binding.btnNego.setBackgroundColor(R.color.abu_muda)
                binding.btnNego.setTextColor(R.color.black)
            } else if (decline) {
                binding.btnNego.setText(R.string.ditolak_text)
                binding.btnNego.isClickable = false
                binding.btnNego.setBackgroundColor(R.color.merah)
            } else {
                binding.btnNego.visibility = View.VISIBLE
            }


            viewModel.getDetail(id)
            viewModel.detail.observe(viewLifecycleOwner) { detail ->
                when (detail.status) {
                    Status.LOADING -> {
                        progressDialog.setMessage("loading")
                        progressDialog.show()
                    }
                    Status.SUCCESS -> {
                        viewModel.detail.observe(viewLifecycleOwner) {
                            binding.apply {
                                tvNamaProduct.text = detail.data?.name
                                tvHarga.text = detail.data?.basePrice.toString()
                                tvLokasi.text = detail.data?.location
                                tvDeskripsi.text = detail.data?.description
                                tvNamaPenjual.text = detail.data?.user?.fullName
                                Glide.with(binding.root)
                                    .load(detail.data?.user?.imageUrl)
                                    .into(ivFotoProfile)
                                Glide.with(binding.root)
                                    .load(detail.data?.imageUrl)
                                    .into(fotoProduk)


                                categoryAdapter = DetailCategoryAdapter(object : DetailCategoryAdapter.OnClickListener{
                                    override fun onClickItem(data: Category) {
                                    }
                                })
                                binding.rvCategory.adapter = categoryAdapter
                                categoryAdapter.submitData(it.data?.categories)

                            }
                        }
                        progressDialog.dismiss()
                    }
                    Status.ERROR -> {
                        AlertDialog.Builder(requireContext()).setMessage("${detail.message}").show()
                        progressDialog.dismiss()
                    }
                }
            }
        }



    }
}