package com.igdev.secondhand.ui.transaksipembeli

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentPengajuanDiterimaBinding
import com.igdev.secondhand.databinding.FragmentSemuaPembelianBinding
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.buyerorder.BuyerOrderResponse
import com.igdev.secondhand.model.local.UserLogin
import com.igdev.secondhand.model.sellerorder.SellerOrderResponseItem
import com.igdev.secondhand.ui.MainFragment
import com.igdev.secondhand.ui.MainFragmentDirections
import com.igdev.secondhand.ui.transaction.BuyerAdapter
import com.igdev.secondhand.ui.transaction.EditOrderFragment
import com.igdev.secondhand.ui.transaction.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransaksiSemuaPembelianFragment : Fragment() {
    private var _binding: FragmentSemuaPembelianBinding? = null
    private val binding get() = _binding!!
    private val viewModel : TransactionViewModel by viewModels()
    private var token : String =""
    private var dataUser : UserLogin?=null
    private val listBuyer: MutableList<BuyerOrderResponse> = ArrayList()
    private val listNego : MutableList<SellerOrderResponseItem> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSemuaPembelianBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainFragment.currentPage = R.id.transactionFragment
        viewModel.getToken()
        viewModel.getToken.observe(viewLifecycleOwner){
            if (it.token=="def_token"){
            }else{
                token=it.token
                viewModel.getBuyerHistory(it.token)
            }
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please Wait...")
        viewModel.getBuyerHistory.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                        progressDialog.show()
                    }
                    Status.SUCCESS -> {
                        if (it.data.isNullOrEmpty()) {
                            binding.emptyNotif.visibility = View.VISIBLE
                            binding.rvAllProduct.visibility = View.GONE
                        } else {
                            listBuyer.clear()
                            for (data in it.data) {
                                if (data.imageProduct.isNullOrEmpty() &&
                                    data.product != null &&
                                    data.basePrice.isEmpty() &&
                                    data.productName.isEmpty() &&
                                    data.transactionDate.isNullOrEmpty() &&
                                    data.product.imageUrl.isNullOrEmpty()
                                ) {
                                } else {
                                    listBuyer.add(data)
                                }
                            }

                            val buyerAdapter =
                                BuyerAdapter(
                                    onClick = {data->
                                        val direct = TransaksiSemuaPembelianFragmentDirections.actionTransaksiSemuaPembelianFragmentToDetailProductFragment(data.productId)
                                        findNavController().navigate(direct)
                                    }, onDelete = { data->
                                        viewModel.deleteOrder(token,data.id)
                                    }, onEdit = {data->
                                        val bottomFragment = EditOrderFragment(
                                            data.id,data.price.toString(),data.productName,data.basePrice,data.product.imageUrl, submit = {req->
                                                viewModel.putOrder(token,data.id,req)
                                                Log.d("price",req.bid_price)
                                            }
                                        )
                                        bottomFragment.show(parentFragmentManager, "Bid Price")
                                        Log.d("id",data.id.toString())
                                        Log.d("token",token)

                                    })
                            if (listBuyer.isEmpty()){
                                binding.emptyNotif.visibility =View.VISIBLE
                                binding.rvAllProduct.visibility = View.GONE
                            }else{
                                binding.rvAllProduct.visibility = View.VISIBLE
                                binding.emptyNotif.visibility = View.GONE
                                buyerAdapter.submitData(listBuyer)
                                binding.rvAllProduct.adapter = buyerAdapter
                            }

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