package com.igdev.secondhand.ui.detailseller.status_penawaran

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentEndPointDiterimaBinding
import com.igdev.secondhand.databinding.FragmentEndoPointDitolakBinding
import com.igdev.secondhand.ui.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EndoPointDitolakFragment : Fragment() {


    private var _binding: FragmentEndoPointDitolakBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEndoPointDitolakBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainFragment.currentPage = R.id.transactionFragment
        binding.root.setOnClickListener {
            findNavController().navigate(R.id.action_endoPointDitolakFragment_to_mainFragment)
        }
    }

}