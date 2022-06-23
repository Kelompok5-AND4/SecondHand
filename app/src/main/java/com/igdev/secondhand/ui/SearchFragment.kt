package com.igdev.secondhand.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import com.igdev.secondhand.lightStatusBar
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentSearchBinding
import com.igdev.secondhand.lightStatusBar

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

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

        val search = arrayOf("Fashion","Electronic","Automotive")
        val resource = android.R.layout.simple_expandable_list_item_1
        val searchAdapter : ArrayAdapter<String> = ArrayAdapter(
            requireContext(),resource,search
        )
        binding.searchList.adapter = searchAdapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                if (search.contains(query)){
                    searchAdapter.filter.filter(query)
                }
                return false
            }
            //push trigger

            override fun onQueryTextChange(newText: String?): Boolean {
                searchAdapter.filter.filter(newText)
                return false
            }

        })
    }
}