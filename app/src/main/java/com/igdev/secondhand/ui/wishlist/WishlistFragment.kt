package com.igdev.secondhand.ui.wishlist

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
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentWishlistBinding
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.wishlist.GetWishlistResponse
import com.igdev.secondhand.model.wishlist.Product
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WishlistViewModel by viewModels()
    private val listWishlist: MutableList<GetWishlistResponse> = ArrayList()
    private val args: WishlistFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getToken()
        viewModel.getToken.observe(viewLifecycleOwner) {
            if (it.token == "def_token") {
                binding.emptyNotif.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.VISIBLE
                binding.rvDaftarSimpan.visibility = View.GONE
            } else {
                viewModel.getAllWishlist(it.token)
                binding.emptyNotif.visibility = View.GONE
                binding.tvEmpty.visibility = View.GONE
                binding.rvDaftarSimpan.visibility = View.VISIBLE
            }
        }
        val progressDialog = ProgressDialog(requireActivity())
        viewModel.getAllWishlist.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    progressDialog.show()
                    progressDialog.setMessage("loading")
                }
                Status.SUCCESS -> {
                    if (it.data.isNullOrEmpty()) {
                        binding.emptyNotif.visibility = View.VISIBLE
                        binding.rvDaftarSimpan.visibility = View.GONE
                    } else {
                        listWishlist.clear()
                        val adapter = WishlistAdapter(object : WishlistAdapter.OnClickListener {
                            override fun onCLickItem(data: GetWishlistResponse) {
                                val id = data.productId
                                val toDetail = WishlistFragmentDirections.actionWishlistFragmentToDetailProductFragment(id)
                                findNavController().navigate(toDetail)
                            }

                        })
                        adapter.submitData(listWishlist)
                        binding.rvDaftarSimpan.adapter = adapter
                        binding.rvDaftarSimpan.visibility = View.VISIBLE
                        binding.emptyNotif.visibility=View.GONE


                        for (data in it.data) {
                            if (data.product != null) {
                                listWishlist.add(data)
                            }
                        }


                    }
                    progressDialog.dismiss()


                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}