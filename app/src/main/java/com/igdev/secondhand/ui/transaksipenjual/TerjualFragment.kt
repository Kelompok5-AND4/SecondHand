package com.igdev.secondhand.ui.transaksipenjual

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
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentSedangDitawarBinding
import com.igdev.secondhand.databinding.FragmentTerjualBinding
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.sellerorder.SellerOrderResponseItem
import com.igdev.secondhand.model.sellerproduct.SellerProductResponseItem
import com.igdev.secondhand.ui.transaction.SellerAdapter
import com.igdev.secondhand.ui.transaction.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TerjualFragment : Fragment() {

    private var _binding: FragmentTerjualBinding? = null
    private val binding get() = _binding!!
    private val viewModel : TransactionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTerjualBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getToken()
        viewModel.getToken.observe(viewLifecycleOwner){
            if (it.token=="def_token"){
            }else{
                viewModel.getSellerProduct(it.token)
            }
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        val progressDialog =ProgressDialog(requireContext())
        progressDialog.setMessage("Please Wait...")
        val negoAdapter =
            SellerAdapter(object : SellerAdapter.OnClickListener {
                override fun onClickItem(data: SellerProductResponseItem) {
                    Toast.makeText(
                        requireContext(),
                        "Product Telah Terjual",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        viewModel.getProductSold.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                        progressDialog.show()
                    }
                    Status.SUCCESS -> {
                        if (it.data.isNullOrEmpty()) {
                            binding.emptyNotif.visibility = View.GONE
                        }
                        else {
                            val filteredData = it.data.filter {
                                it.status == "seller"
                            }
                            if (filteredData.isEmpty()){
                                binding.emptyNotif.visibility = View.VISIBLE
                            }else{
                                negoAdapter.submitData(filteredData)
                                binding.rvKategori.adapter = negoAdapter}
                        }
                        progressDialog.dismiss()
                    }
                    Status.ERROR -> {
                        progressDialog.dismiss()
                        AlertDialog.Builder(requireContext())
                            .setMessage(it.message)
                            .show()
                    }
                }
            }
        }
    }


}