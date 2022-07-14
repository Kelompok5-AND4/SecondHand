package com.igdev.secondhand.ui.transaksipembeli

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentAccountBinding
import com.igdev.secondhand.databinding.FragmentPenawaranTerkirimBinding
import com.igdev.secondhand.model.local.UserLogin
import com.igdev.secondhand.ui.viewmodel.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PenawaranTerkirimFragment : Fragment() {
    private var _binding: FragmentPenawaranTerkirimBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPenawaranTerkirimBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnClickListener {
            val direct = PenawaranTerkirimFragmentDirections.actionPenawaranTerkirimFragmentToMainFragment()
            findNavController().navigate(direct)
        }
    }

}