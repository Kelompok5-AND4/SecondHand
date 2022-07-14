package com.igdev.secondhand.ui.settingaccount

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentSettingBinding
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.settingaccount.SettingAccountRequest
import com.igdev.secondhand.ui.viewmodel.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AccountViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val password = binding.etPassword.text
        val newPassword = binding.etNewPassword.text
        val repassword = binding.etRepassword.text

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_mainFragment)
        }
        binding.btnChangePassword.setOnClickListener {
            when {
                password.isNullOrEmpty() -> {
                    binding.tiUserCurrentPassword.error = "masukkan password lama"
                }
                newPassword.isNullOrEmpty() -> {
                    binding.tiUserNewPassword.error = "masukkan password baru"
                }
                repassword.isNullOrEmpty() -> {
                    binding.tiUserRepassword.error = " masukkan ulang password baru"
                }
                else -> {
                    viewModel.getToken()
                    viewModel.getToken.observe(viewLifecycleOwner) {
                        val reqRepass = SettingAccountRequest(
                            password.toString(),
                            newPassword.toString(),
                            repassword.toString()
                        )
                        viewModel.settingAccount(it.token, reqRepass)
                    }

                }
            }

        }

        val progressDialog = ProgressDialog(requireActivity())
        viewModel.setAccount.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    progressDialog.show()
                    progressDialog.setMessage("loading")
                }
                Status.SUCCESS -> {
                    Toast.makeText(
                        requireContext(),
                        "Succes ganti password",
                        Toast.LENGTH_SHORT
                    ).show()
                    progressDialog.dismiss()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Gagal ganti password", Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                }
            }
        }
    }

}