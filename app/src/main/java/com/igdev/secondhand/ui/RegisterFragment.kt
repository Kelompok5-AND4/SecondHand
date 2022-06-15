package com.igdev.secondhand.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.igdev.secondhand.RegisterViewModel
import com.igdev.secondhand.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel : RegisterViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvMasukDiSini.setOnClickListener {
                signIn()
            }
        }

    }

    private fun signIn() {
        val nama = binding.etNamaLengkap.text
        val email = binding.etEmail.text
        val password = binding.etBuatPassword.text

        if (nama.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(requireContext(), "lengkapi data", Toast.LENGTH_SHORT).show()
        }else{

        }
    }

}