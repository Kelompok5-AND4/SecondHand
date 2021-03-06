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
import com.igdev.secondhand.databinding.FragmentSemuaPenjualanBinding
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.buyerorder.BuyerOrderResponse
import com.igdev.secondhand.model.local.UserLogin
import com.igdev.secondhand.model.sellerorder.SellerOrderResponseItem
import com.igdev.secondhand.model.sellerproduct.SellerProductResponseItem
import com.igdev.secondhand.ui.MainFragmentDirections
import com.igdev.secondhand.ui.transaction.SellerAdapter
import com.igdev.secondhand.ui.transaction.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SedangDitawarFragment : Fragment() {

    private var _binding: FragmentSedangDitawarBinding? = null
    private val binding get() = _binding!!
    private val viewModel : TransactionViewModel by viewModels()
    private var token : String =""
    private var dataUser : UserLogin?=null
    private val listBuyer: MutableList<BuyerOrderResponse> = ArrayList()
    private val listNego : MutableList<SellerOrderResponseItem> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSedangDitawarBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getToken()
        viewModel.getToken.observe(viewLifecycleOwner){
            if (it.token=="def_token"){
            }else{
                viewModel.getSellerOrder(it.token)
            }
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        val negoAdapter =
            NegoAdapter(object : NegoAdapter.OnClickListener {
                override fun onClickItem(data: SellerOrderResponseItem) {
                    val direct = SedangDitawarFragmentDirections.actionSedangDitawarFragmentToPenawaranBuyerToSellerFragment(data.id)
                    findNavController().navigate(direct)
                }
            })
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please Wait...")
        viewModel.getAllSellerOrder.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                        progressDialog.show()
                    }
                    Status.SUCCESS -> {
                        if (it.data.isNullOrEmpty()) {
                            binding.emptyNotif.visibility = View.GONE
                        } else {
                            listNego.clear()
                            for (res in it.data){
                                if (res.status == "pending" || res.status == "accepted"){
                                    listNego.add(res)
                                }
                            }
                            if (listNego.isEmpty()){
                                binding.emptyNotif.visibility = View.VISIBLE
                            }else{
                                negoAdapter.submitData(listNego)
                                binding.rvNego.adapter = negoAdapter}
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