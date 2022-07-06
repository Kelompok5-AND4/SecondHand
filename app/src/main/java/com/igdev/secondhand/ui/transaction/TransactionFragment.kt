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
import com.igdev.secondhand.model.local.UserLogin
import com.igdev.secondhand.model.notification.NotifResponseItem
import com.igdev.secondhand.ui.MainFragment
import com.igdev.secondhand.ui.MainFragmentDirections
import com.igdev.secondhand.ui.viewmodel.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList


@AndroidEntryPoint
class TransactionFragment : Fragment() {

    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!
    private val viewModel : TransactionViewModel by viewModels()
    private var token : String =""
    private var dataUser : UserLogin?=null
    private val listNotif: MutableList<NotifResponseItem> = ArrayList()

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
                viewModel.getAllNotif(it.token)
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
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please Wait...")
        viewModel.getNotif.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                        progressDialog.show()
                    }
                    Status.SUCCESS -> {
                        if (it.data.isNullOrEmpty()) {
                            binding.emptyNotif.visibility = View.VISIBLE
                        } else {
                            for (data in it.data) {
                                if (data.imageUrl.isNullOrEmpty() &&
                                    data.basePrice.isEmpty() &&
                                    data.product != null &&
                                    data.bidPrice.toString().isEmpty() &&
                                    data.productName.isEmpty() &&
                                    data.transactionDate.isNullOrEmpty() &&
                                    data.receiverId == data.product.userId
                                ) {
                                } else {
                                    listNotif.add(data)
                                }
                            }
                            val buyerAdapter =
                                BuyerAdapter(object : BuyerAdapter.OnClickListener {
                                    override fun onClickItem(data: NotifResponseItem) {
                                        Toast.makeText(
                                            requireContext(),
                                            "Notif Id = ${data.id}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                })
                            buyerAdapter.submitData(listNotif)
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
    }

}