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
import com.igdev.secondhand.databinding.FragmentRegisterBinding
import com.igdev.secondhand.model.register.RegistReq
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.ui.viewmodel.RegisterViewModel
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

        val progressDialog = ProgressDialog(requireActivity())
        viewModel.userRegister.observe(viewLifecycleOwner){ resources ->
            when(resources.status){
                Status.LOADING ->{
                    progressDialog.setMessage("Loading")
                    progressDialog.show()
                }
                Status.SUCCESS ->{
                    Toast.makeText(requireContext(), "Registrasi berhasil", Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

                }
                Status.ERROR ->{
                    Toast.makeText(requireContext(), "Registrasi gagal", Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                }
            }
        }

        binding.apply {
            btnRegister.setOnClickListener {
                signIn()
            }
        }

    }

    private fun signIn() {
        val nama = binding.etNamaLengkap.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etBuatPassword.text.toString()
        val phone = binding.phoneNumber.text.toString()/*
        val location = binding.location.text.toString()*/
        val image = "no_image"
        val registerReq = RegistReq(nama,email,password,phone,"location",image,"jakarta")
        viewModel.registerPost(registerReq)
        if (nama.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(requireContext(), "lengkapi data", Toast.LENGTH_SHORT).show()
        }
    }

}