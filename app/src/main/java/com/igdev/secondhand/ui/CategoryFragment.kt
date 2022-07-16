package com.igdev.secondhand.ui

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
import com.igdev.secondhand.databinding.FragmentCategoryBinding
import com.igdev.secondhand.model.AllProductResponse
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.ui.category.CategoryBarangAdapter
import com.igdev.secondhand.ui.category.CategoryBarangViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoryBarangViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryBarangAdapter
    private val args: CategoryFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        val id = args.id.toString()
        val status = "available"
        val progressDialog = ProgressDialog(requireContext())
        viewModel.getProduct(status, id)
        viewModel.category.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    progressDialog.setMessage("Loading")
                    progressDialog.show()
                }
                Status.SUCCESS -> {
                    categoryAdapter =
                        CategoryBarangAdapter(object : CategoryBarangAdapter.OnClickListener {
                            override fun onClickItem(data: AllProductResponse) {
                                val id = data.id
                                val toDetail = CategoryFragmentDirections.actionCategoryFragmentToDetailProductFragment(id)
                                findNavController().navigate(toDetail)
                            }
                        })
                    binding.rvKategori.adapter = categoryAdapter
                    resource.data?.let {
                        categoryAdapter.submitData(it.body()!!)
                    }
                    progressDialog.dismiss()
                }
                Status.ERROR -> {
                    AlertDialog.Builder(requireContext()).setMessage("${resource.message}").show()
                    progressDialog.dismiss()

                }
            }

        }
    }

}