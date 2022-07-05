package com.igdev.secondhand.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentAccountBinding
import com.igdev.secondhand.databinding.FragmentLoginBinding
import com.igdev.secondhand.ui.viewmodel.AccountViewModel
import com.igdev.secondhand.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel : AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainFragment.currentPage = R.id.accountFragment

        viewModel.getToken()
        viewModel.getToken.observe(viewLifecycleOwner){
            if (it.token == "def_token"){
                binding.notLogin.visibility = View.VISIBLE
                binding.menuContainer.visibility = View.GONE
            }
            else {
                binding.notLogin.visibility = View.GONE
                binding.menuContainer.visibility = View.VISIBLE
            }
        }
        binding.bgSectionEdit.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_editAccountFragment)
        }
        binding.bgSectionLogout.setOnClickListener {
            viewModel.deleteToken()
            findNavController().navigate(R.id.action_mainFragment_to_splashFragment)
        }
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }
    }

    }