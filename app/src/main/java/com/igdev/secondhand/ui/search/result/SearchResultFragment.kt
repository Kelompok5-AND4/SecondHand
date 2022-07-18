package com.igdev.secondhand.ui.search.result

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
import com.igdev.secondhand.database.SearchHistoryEntity
import com.igdev.secondhand.databinding.FragmentSearchBinding
import com.igdev.secondhand.databinding.FragmentSearchResultBinding
import com.igdev.secondhand.model.AllProductResponse
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.ui.MainFragmentDirections
import com.igdev.secondhand.ui.adapter.ProductAdapter
import com.igdev.secondhand.ui.search.SearchViewModel
import com.igdev.secondhand.ui.search.listpage.SearchHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchResultFragment : Fragment() {
    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchResultAdapter
    private val args: SearchResultFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SearchResultAdapter(object : SearchResultAdapter.OnClickListener{
            override fun onClickItem(data: AllProductResponse) {
                val id = data.id
                val toDetail = SearchResultFragmentDirections.actionSearchResultFragmentToDetailProductFragment(id)
                findNavController().navigate(toDetail)
            }
        })
        getProduct()
        setUpSearchBarListener()
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
    private fun getProduct() {
        val progressDialog = ProgressDialog(requireContext())
        viewModel.getProduct(searchKeyword = args.query)
        viewModel.searchResponse.observe(viewLifecycleOwner){ resources ->
            when(resources.status){
                Status.LOADING ->{
                    progressDialog.setMessage("Loading")
                    progressDialog.show()
                }
                Status.SUCCESS ->{

                    when(resources.data?.code()){
                        200 ->{
                            adapter.submitData(resources.data.body())
                            progressDialog.dismiss()
                            binding.rvSearchResult.adapter = adapter
                        }
                    }



                }
                Status.ERROR ->{
                    Toast.makeText(requireContext(), resources.message, Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                }
            }
        }
    }

    private fun setUpSearchBarListener() {
        binding.etSearch.editText?.setOnFocusChangeListener { view, search ->
            if (search) {
                searchProduct()
            }
        }
    }

    private fun searchProduct() {
        val currentDestination =
            this.findNavController().currentDestination?.id == R.id.searchResultFragment
        if (currentDestination){
            this.findNavController().navigate(R.id.action_searchResultFragment_to_searchFragment)
        }
    }

}