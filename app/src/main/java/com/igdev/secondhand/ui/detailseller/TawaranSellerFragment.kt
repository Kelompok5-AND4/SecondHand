package com.igdev.secondhand.ui.detailseller

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentRegisterBinding
import com.igdev.secondhand.databinding.FragmentTawaranSellerBinding
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.buyerorder.BuyerOrderResponse
import com.igdev.secondhand.model.detail.Category
import com.igdev.secondhand.model.sellerorder.SellerOrderResponseItem
import com.igdev.secondhand.model.sellerproduct.SellerProductResponseItem
import com.igdev.secondhand.rupiah
import com.igdev.secondhand.ui.MainFragmentDirections
import com.igdev.secondhand.ui.adapter.DetailCategoryAdapter
import com.igdev.secondhand.ui.transaction.SellerAdapter
import com.igdev.secondhand.ui.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TawaranSellerFragment : Fragment() {
    private var _binding: FragmentTawaranSellerBinding? = null
    private val binding get() = _binding!!
    private val viewModel : TawaranSellerViewModel by viewModels()
    val args:TawaranSellerFragmentArgs by navArgs()
    private val listPenawar: MutableList<SellerOrderResponseItem> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTawaranSellerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        viewModel.getToken()
        viewModel.getToken.observe(viewLifecycleOwner){
            viewModel.getSellerOrder(it.token)
            viewModel.getDetail(it.token,id)
        }
        binding.btnEdit.setOnClickListener {
            val direct = TawaranSellerFragmentDirections.actionTawaranSellerFragmentToEditProductFragment(id)
            findNavController().navigate(direct)
        }
        val progressDialog = ProgressDialog(requireContext())
        viewModel.detail.observe(viewLifecycleOwner){detail ->
            when (detail.status) {
                Status.LOADING -> {
                    progressDialog.setMessage("loading")
                    progressDialog.show()
                }
                Status.SUCCESS -> {
                    binding.apply {
                        tvNamaProduct.text = detail.data?.name
                        tvHarga.text = rupiah(detail.data?.basePrice.toString().toInt())
                        Glide.with(binding.root)
                            .load(detail.data?.imageUrl)
                            .into(fotoProduk)
                    }
                    progressDialog.dismiss()
                }
                Status.ERROR -> {
                    AlertDialog.Builder(requireContext()).setMessage("${detail.message}").show()
                    progressDialog.dismiss()
                }
            }
        }
        viewModel.getAllSellerOrder.observe(viewLifecycleOwner){

            val penawarAdapter =
                PenawarAdapter(object : PenawarAdapter.OnClickListener {
                    override fun onClickItem(data: SellerOrderResponseItem) {
                        val direct = TawaranSellerFragmentDirections.actionTawaranSellerFragmentToPenawaranBuyerToSellerFragment(data.id)
                        findNavController().navigate(direct)
                    }
                })
            listPenawar.clear()
            if (it.status == Status.SUCCESS){
                for (res in it.data!!){
                    if (res.productId == id){
                        listPenawar.add(res)
                    }
                }
                penawarAdapter.submitData(listPenawar)
                binding.rvTawar.adapter = penawarAdapter
            }
        }
    }
}