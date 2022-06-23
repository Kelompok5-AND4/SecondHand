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
import com.igdev.secondhand.databinding.FragmentLoginBinding
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.login.LoginReq
import com.igdev.secondhand.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel : LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressDialog = ProgressDialog(requireActivity())
        viewModel.userLogin.observe(viewLifecycleOwner){ resources ->
            when(resources.status){
                Status.LOADING ->{
                    progressDialog.setMessage("Loading")
                    progressDialog.show()
                }
                Status.SUCCESS ->{
                    Toast.makeText(requireContext(), "Login berhasil", Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                Status.ERROR ->{
                    Toast.makeText(requireContext(), "Login gagal", Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                }
            }
        }

        binding.apply {
            btnLogin.setOnClickListener{
                login()
            }
            tvDaftarDiSini.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            tvMasukDebug.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_debugFragment)
            }
        }
    }

    private fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val loginReq = LoginReq(email,password)
        viewModel.loginPost(loginReq)
    }

}