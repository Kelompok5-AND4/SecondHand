package com.igdev.secondhand.ui.lelang

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.igdev.secondhand.databinding.FragmentLelangBinding
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.sellerproduct.SellerProductResponseItem
import com.igdev.secondhand.ui.transaction.SellerAdapter
import com.igdev.secondhand.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LelangFragment : Fragment() {
    private var _binding : FragmentLelangBinding? = null
    private val binding get() = _binding!!
    private val viewModel : HomeViewModel by viewModels()
    private val tokenLelang =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImN1YW43N0BtYWlsLmNvbSIsImlhdCI6MTY1ODQ3OTg3MH0.yxkRJ5mJMv1IUiQkJVuKJNxa9wyjd4u7u8R6MlaPGWg"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLelangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSellerProduct(tokenLelang)
        binding.ibIcBack.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.getAllSellerProduct.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        if (it.data.isNullOrEmpty()) {
                            binding.rvLelang.visibility = View.GONE
                        } else {
                            val sellerAdapter =
                                SellerAdapter(object : SellerAdapter.OnClickListener {
                                    override fun onClickItem(data: SellerProductResponseItem) {
                                        val id = data.id
                                        val toDetail =LelangFragmentDirections.actionLelangFragmentToDetailProductFragment(
                                                id
                                            )
                                        findNavController().navigate(toDetail)
                                    }
                                })
                            sellerAdapter.submitData(it.data)
                            binding.rvLelang.adapter = sellerAdapter
                        }
                    }
                    Status.ERROR -> {
                        AlertDialog.Builder(requireContext())
                            .setMessage(it.message)
                            .show()
                    }
                }
            }
        }
        
    }
}