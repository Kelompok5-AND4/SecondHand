package com.igdev.secondhand.ui

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
import com.igdev.secondhand.databinding.FragmentHomeBinding
import com.igdev.secondhand.model.AllProductResponse
import com.igdev.secondhand.model.CategoryResponseItem
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.ui.adapter.CategoryAdapter
import com.igdev.secondhand.ui.adapter.MiniCategoryAdapter
import com.igdev.secondhand.ui.adapter.ProductAdapter
import com.igdev.secondhand.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel:HomeViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var miniCategoryAdapter: MiniCategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
        binding.searchIcon.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
        productAdapter = ProductAdapter(object : ProductAdapter.OnClickListener{
            override fun onClickItem(data: AllProductResponse) {
                Toast.makeText(requireContext(),"click",Toast.LENGTH_SHORT).show()
            }
        })
        binding.rvBanner.adapter = productAdapter
        categoryAdapter = CategoryAdapter(object : CategoryAdapter.OnClickListener{
            override fun onClickItem(data: CategoryResponseItem) {
                Toast.makeText(requireContext(),"click",Toast.LENGTH_SHORT).show()
            }
        })
        binding.rvCategory.adapter = categoryAdapter
        miniCategoryAdapter = MiniCategoryAdapter(object : MiniCategoryAdapter.OnClickListener{
            override fun onClickItem(data: CategoryResponseItem) {
                Toast.makeText(requireContext(),"click",Toast.LENGTH_SHORT).show()
            }
        })
        binding.rvMiniCategory.adapter = miniCategoryAdapter
        getCategory()
        getBanner()

    }

    private fun getCategory(){
        viewModel.getCategory()
        viewModel.category.observe(viewLifecycleOwner){ resources ->
            if (resources.status == Status.SUCCESS){
                categoryAdapter.submitData(resources.data)
                miniCategoryAdapter.submitData(resources.data)
            }
        }
    }

    private fun getBanner() {
        val status = "available"
        val categoryId = ""
        val progressDialog = ProgressDialog(requireContext())
        viewModel.product.observe(viewLifecycleOwner){ resources ->
            when(resources.status){
                Status.LOADING ->{
                    progressDialog.setMessage("Loading")
                    progressDialog.show()
                }
                Status.SUCCESS ->{
                    productAdapter.submitData(resources.data)
                    progressDialog.dismiss()
                }
                Status.ERROR ->{
                    Toast.makeText(requireContext(), resources.message, Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                }
            }
        }
        viewModel.getProduct(status, categoryId)
    }
}