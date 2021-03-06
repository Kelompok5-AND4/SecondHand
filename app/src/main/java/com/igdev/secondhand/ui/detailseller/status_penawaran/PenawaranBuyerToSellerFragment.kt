package com.igdev.secondhand.ui.detailseller.status_penawaran

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.opengl.Visibility
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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentPenawaranBuyerToSellerBinding
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.sellerorder.PatchSellerOrderReq
import com.igdev.secondhand.model.sellerorder.SellerOrderResponseItem
import com.igdev.secondhand.ui.detailseller.BottomSheetWAFragment
import com.igdev.secondhand.ui.detailseller.TawaranSellerFragmentArgs
import com.igdev.secondhand.ui.detailseller.TawaranSellerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PenawaranBuyerToSellerFragment : Fragment() {

    private var _binding: FragmentPenawaranBuyerToSellerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TawaranSellerViewModel by viewModels()
    val args: PenawaranBuyerToSellerFragmentArgs by navArgs()
    private var orderId: Int? = null
    private var status: String? = null
    private var token = ""
    private var city = ""
    private var bid = ""
    private var base = ""
    private var phoneNumber = ""
    private var buyerName = ""
    private var productName = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPenawaranBuyerToSellerBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idOrder = args.id
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.getToken()
        viewModel.getToken.observe(viewLifecycleOwner){
            viewModel.bidder(it.token,idOrder)
            token = it.token
        }
        viewModel.bidder.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    orderId = it.data?.body()?.id.toString().toInt()
                    status = it.data?.body()?.status.toString()
                    binding.tvNamaPembeli.text = it.data?.body()?.user?.fullName.toString()
                    binding.tvLokasi.text = it.data?.body()?.user?.city.toString()
                    Glide.with(this)
                        .load(it.data?.body()?.product?.imageUrl)
                        .centerCrop()
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(12)))
                        .into(binding.fotoProduk)
                    binding.tvNamaProduct.text = it.data?.body()?.product?.name.toString()
                    binding.tvHarga.text = it.data?.body()?.basePrice.toString()
                    binding.tvHargaTawaran.text = it.data?.body()?.price.toString()
                    phoneNumber = it.data?.body()?.user?.phoneNumber.toString()
                    base = it.data?.body()?.product?.basePrice.toString()
                    bid = it.data?.body()?.price.toString()
                    productName = it.data?.body()?.productName.toString()
                    if (it.data?.body()?.status == "accepted"){
                        binding.btnGroupPending.visibility = View.GONE
                        binding.btnGroupAcc.visibility = View.VISIBLE
                    }
                    val buyer = it.data?.body()?.user?.fullName.toString()
                    buyerName = buyer
                    val citya =it.data?.body()?.user?.city.toString()
                    city = citya
                    val productId = it.data?.body()?.productId.toString().toInt()
                    when (it.data?.body()?.status) {
                        "declined" -> {
                            binding.tvHargaTawaran.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                            binding.btnTerima.visibility = View.GONE
                            binding.btnTolak.visibility = View.GONE
                        }
                        "accepted" -> {
                            binding.tvHargaTawaran.paintFlags = 0
                        }

                    }
                    binding.btnStatus.setOnClickListener {
                        val bottomFragment = StatusProductFragment(
                            productId,token,buyer , city,submit = {req->
                                viewModel.statusProduct(token,productId,PatchSellerOrderReq(req))
                                if (req == "seller"){
                                    Toast.makeText(requireContext(),"Produk Berhasil Terjual",Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.action_penawaranBuyerToSellerFragment_to_endPointDiterimaFragment)
                                }else{
                                    Toast.makeText(requireContext(),"Penawaran Telah Ditolak",Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.action_penawaranBuyerToSellerFragment_to_endoPointDitolakFragment)
                                }
                            }
                        )
                        bottomFragment.show(parentFragmentManager, "Bid Price")
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Unknown Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.status.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    viewModel.bidder(token,args.id)
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Unknown Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnTolak.setOnClickListener {
            status?.let {
                if (it == "pending") {
                    viewModel.statusItem(token,idOrder, PatchSellerOrderReq(status = "declined"))
                    status="declined"
                } else {
                    Toast.makeText(requireContext(),"Unknown Error",Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.btnTerima.setOnClickListener {
            viewModel.statusItem(token,idOrder, PatchSellerOrderReq(status = "accepted"))
            status="accepted"
            val bottomFragment = BottomSheetWAFragment(
                buyerName , city,phoneNumber,base.toInt(),bid.toInt(),productName,submit = {
                    val nomor = number(phoneNumber)
                    val text = "Halo $buyerName,Terimakasih Telah Menawar barang yang saya jual, untuk lebih jelasnya apakah ingin lewat chat atau ketemu langsung?"
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                "https://api.whatsapp.com/send?phone=$nomor&text=$text"
                            )
                        )
                    )
                }
            )
            bottomFragment.show(parentFragmentManager, "Bid Price")

        }
        binding.btnHubungi.setOnClickListener {
            val bottomFragment = BottomSheetWAFragment(
                buyerName , city,phoneNumber,base.toInt(),bid.toInt(),productName,submit = {
                    val nomor = number(phoneNumber)
                    val text = "Halo $buyerName,Terimakasih Telah Menawar barang yang saya jual, untuk lebih jelasnya apakah ingin lewat chat atau ketemu langsung?"
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                "https://api.whatsapp.com/send?phone=$nomor&text=$text"
                            )
                        )
                    )
                }
            )
            bottomFragment.show(parentFragmentManager, "Bid Price")
        }
    }
    private fun number(nomorPembeli:String): String {
        return when {
            nomorPembeli.take(1) == "0" -> {
                var number = nomorPembeli.drop(1)
                number = "62$number"
                number
            }
            nomorPembeli.take(2) == "62" -> {
                nomorPembeli
            }
            else -> {
                "62$nomorPembeli"
            }
        }
    }
}