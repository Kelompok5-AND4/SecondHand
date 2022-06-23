package com.igdev.secondhand

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.igdev.secondhand.databinding.FragmentDebugBinding


class DebugFragment : Fragment() {
    private var _bindig : FragmentDebugBinding? = null
    private val binding get() = _bindig!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bindig = FragmentDebugBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDebugAccount.setOnClickListener {
            findNavController().navigate(R.id.action_debugFragment_to_accountFragment)
        }

        binding.btnSearchbar.setOnClickListener{
            findNavController().navigate(R.id.action_debugFragment_to_searchFragment)
        }

        binding.btnLelang.setOnClickListener {
            findNavController().navigate(R.id.action_debugFragment_to_lelangFragment)
        }
    }
}
