package com.igdev.secondhand

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.igdev.secondhand.databinding.FragmentHomeBinding
import com.igdev.secondhand.databinding.FragmentStartBinding
import com.igdev.secondhand.ui.adapter.CategoryAdapter
import com.igdev.secondhand.ui.adapter.MiniCategoryAdapter
import com.igdev.secondhand.ui.adapter.ProductAdapter
import com.igdev.secondhand.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnMasuk.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_loginFragment)
        }
        binding.btnTanpaAkun.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_mainFragment)
        }
    }
}