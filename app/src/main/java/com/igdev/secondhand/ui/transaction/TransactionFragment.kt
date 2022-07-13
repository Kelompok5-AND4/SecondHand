package com.igdev.secondhand.ui.transaction

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
import com.igdev.secondhand.databinding.FragmentAccountBinding
import com.igdev.secondhand.databinding.FragmentTransactionBinding
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.buyerorder.BuyerOrderResponse
import com.igdev.secondhand.model.local.UserLogin
import com.igdev.secondhand.model.notification.NotifResponseItem
import com.igdev.secondhand.model.sellerorder.SellerOrderResponseItem
import com.igdev.secondhand.model.sellerproduct.SellerProductResponseItem
import com.igdev.secondhand.ui.MainFragment
import com.igdev.secondhand.ui.MainFragmentDirections
import com.igdev.secondhand.ui.viewmodel.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TransactionFragment : Fragment() {

    private var _binding: FragmentTransactionBinding? = null
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
        _binding = FragmentTransactionBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainFragment.currentPage = R.id.transactionFragment
        viewModel.getToken()
        viewModel.getToken.observe(viewLifecycleOwner){
            if (it.token=="def_token"){
                binding.notLogin.visibility =View.VISIBLE
                binding.loggedIn.visibility = View.GONE
            }else{
                viewModel.getBuyerHistory(it.token)
                viewModel.getSellerProduct(it.token)
                viewModel.getSellerOrder(it.token)
                binding.notLogin.visibility =View.GONE
                binding.loggedIn.visibility = View.VISIBLE
            }
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnLogin.setOnClickListener {
            val direct = MainFragmentDirections.actionMainFragmentToLoginFragment()
            findNavController().navigate(direct)
        }
        binding.cvAdd.setOnClickListener {
            MainFragment.currentPage = R.id.sellFragment
            findNavController().navigate(R.id.mainFragment)
        }
        binding.btnPlus.setOnClickListener {
            MainFragment.currentPage = R.id.sellFragment
            findNavController().navigate(R.id.mainFragment)
        }
        binding.cvSemua.setOnClickListener{
            val direct = MainFragmentDirections.actionMainFragmentToSemuaPenjualanFragment()
            findNavController().navigate(direct)
        }
        binding.cvDitawar.setOnClickListener {
            val direct = MainFragmentDirections.actionMainFragmentToSedangDitawarFragment()
            findNavController().navigate(direct)
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
                            binding.rvHistori.visibility = View.GONE
                        } else {
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
                                BuyerAdapter(object : BuyerAdapter.OnClickListener {
                                    override fun onClickItem(data: BuyerOrderResponse) {
                                        Toast.makeText(
                                            requireContext(),
                                            "Notif Id = ${data.id}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val direct = MainFragmentDirections.actionMainFragmentToDetailProductFragment(data.productId)
                                        findNavController().navigate(direct)
                                    }
                                })

                            binding.emptyNotif.visibility = View.GONE
                            buyerAdapter.submitData(listBuyer)
                            binding.rvHistori.adapter = buyerAdapter
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
        binding.apply {
            btnSemua.setOnClickListener {
                val direct = MainFragmentDirections.actionMainFragmentToTransaksiSemuaPembelianFragment()
                findNavController().navigate(direct)
            }
            btnPengajuan.setOnClickListener {
                val direct = MainFragmentDirections.actionMainFragmentToDalamPengajuanFragment()
                findNavController().navigate(direct)
            }
            btnDiterima.setOnClickListener {
                val direct = MainFragmentDirections.actionMainFragmentToPengajuanDiterimaFragment()
                findNavController().navigate(direct)
            }
            btnDitolak.setOnClickListener {
                val direct = MainFragmentDirections.actionMainFragmentToPengajuanDitolakFragment()
                findNavController().navigate(direct)
            }
        }
        viewModel.getAllSellerProduct.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                        progressDialog.show()
                    }
                    Status.SUCCESS -> {
                        if (it.data.isNullOrEmpty()) {
                            binding.tvTotal.text = "00"
                        } else {
                            val sellerAdapter =
                                SellerAdapter(object : SellerAdapter.OnClickListener {
                                    override fun onClickItem(data: SellerProductResponseItem) {
                                        val direct = MainFragmentDirections.actionMainFragmentToTawaranSellerFragment(data.id)
                                        findNavController().navigate(direct)
                                    }
                                })
                            sellerAdapter.submitData(it.data)
                            binding.rvSemuaProduct.adapter = sellerAdapter
                            binding.tvTotal.text = "0${sellerAdapter.itemCount}"
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

        viewModel.getAllSellerOrder.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                        progressDialog.show()
                    }
                    Status.SUCCESS -> {
                        if (it.data.isNullOrEmpty()) {
                            binding.tvTotalDitawar.text = "00"
                        } else {
                            binding.tvTotalDitawar.text = "0${it.data.size}"
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