package com.igdev.secondhand.ui.detailseller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentRegisterBinding
import com.igdev.secondhand.databinding.FragmentTawaranSellerBinding
import com.igdev.secondhand.ui.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TawaranSellerFragment : Fragment() {
    private var _binding: FragmentTawaranSellerBinding? = null
    private val binding get() = _binding!!
    private val viewModel : TawaranSellerViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTawaranSellerBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = 1517
        viewModel.getToken()
        viewModel.getToken.observe(viewLifecycleOwner){
            viewModel.getSellerOrder(it.token)
            viewModel.getDetail(it.token,id)
        }
    }
}