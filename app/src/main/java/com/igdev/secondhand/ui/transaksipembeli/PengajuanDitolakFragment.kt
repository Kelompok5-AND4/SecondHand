package com.igdev.secondhand.ui.transaksipembeli

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentPengajuanDitolakBinding
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.buyerorder.BuyerOrderResponse
import com.igdev.secondhand.model.local.UserLogin
import com.igdev.secondhand.model.sellerorder.SellerOrderResponseItem
import com.igdev.secondhand.ui.MainFragment
import com.igdev.secondhand.ui.transaction.BuyerAdapter
import com.igdev.secondhand.ui.transaction.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PengajuanDitolakFragment : Fragment() {
    private var _binding: FragmentPengajuanDitolakBinding? = null
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
        _binding = FragmentPengajuanDitolakBinding.inflate(inflater,container, false)
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
                            binding.rvDitolak.visibility = View.GONE
                        } else {
                            listBuyer.clear()
                            for (data in it.data) {
                                if (data.status == "declined"
                                ) {listBuyer.add(data)
                                }
                            }
                            val buyerAdapter =
                                AccDcBuyerOrderAdapter(object : AccDcBuyerOrderAdapter.OnClickListener {
                                    override fun onClickItem(data: BuyerOrderResponse) {
                                        val direct = PengajuanDitolakFragmentDirections.actionPengajuanDitolakFragmentToDetailProductFragment(data.productId)
                                        findNavController().navigate(direct)
                                    }
                                })
                            binding.emptyNotif.visibility = View.GONE
                            buyerAdapter.submitData(listBuyer)
                            binding.rvDitolak.adapter = buyerAdapter
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