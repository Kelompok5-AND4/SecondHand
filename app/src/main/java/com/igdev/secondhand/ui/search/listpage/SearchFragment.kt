package com.igdev.secondhand.ui.search.listpage

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.igdev.secondhand.R
import com.igdev.secondhand.database.SearchHistoryEntity
import com.igdev.secondhand.databinding.FragmentSearchBinding
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.ui.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private var listHistory: List<SearchHistoryEntity>? = null
    private lateinit var adapter: SearchHistoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.viewBackgroundFasion.setOnClickListener {

            val action = SearchFragmentDirections
                .actionSearchFragmentToSearchResultFragment("sepatu")
            findNavController().navigate(action)
        }
        binding.viewBackgroundSmarthpone.setOnClickListener {
            val action = SearchFragmentDirections
                .actionSearchFragmentToSearchResultFragment("handphone")
            findNavController().navigate(action)
        }
        binding.viewBackgroundLelang.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_lelangFragment)
        }

        binding.etSearch.editText?.requestFocus()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.etSearch.editText, InputMethodManager.SHOW_IMPLICIT)
        viewModel.getSearchHistory()
        viewModel.searchHistory.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    listHistory = it.data
                    showSearchHistory(it.data)
                }

                Status.ERROR -> {
                    AlertDialog.Builder(requireContext())
                        .setMessage(it.message)
                        .setPositiveButton("Ok") { dialog, _ ->
                            dialog.dismiss()
                            findNavController().popBackStack()
                        }
                        .show()
                }
            }
        }
        searchInputListener()
        searchBarListener()


    }

    private fun searchBarListener() {
        binding.etSearch.setEndIconOnClickListener {
            //do search
            searchProduct(binding.etSearch.editText?.text.toString())
        }

        binding.etSearch.editText?.setOnEditorActionListener { _, i, _ ->

            if (i == EditorInfo.IME_ACTION_SEARCH) {
                //do something with search
                searchProduct(binding.etSearch.editText?.text.toString())
            }

            true
        }
    }

    private fun searchProduct(query: String) {
        //insert data to room
        if (query.isNotEmpty()) {
            viewModel.insertSearchHistory(SearchHistoryEntity(searchKeyword = query))

            val action = SearchFragmentDirections
                .actionSearchFragmentToSearchResultFragment(query)
            findNavController().navigate(action)
        }
    }


    private fun searchInputListener() {
        binding.etSearch.editText?.addTextChangedListener { keyword ->
            val filteredList =
                listHistory?.filter { it.searchKeyword.contains(keyword.toString()) }

            adapter.submitList(filteredList)
        }
    }

    private fun showSearchHistory(data: List<SearchHistoryEntity>?) {
        adapter = SearchHistoryAdapter {
            //onclick item
            val action =
                SearchFragmentDirections
                    .actionSearchFragmentToSearchResultFragment(it.searchKeyword)
            findNavController().navigate(action)
        }

        adapter.submitList(data)

        binding.rvSearch.adapter = adapter
    }
}