package com.igdev.secondhand.ui.account

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentAccountBinding
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.local.UserLogin
import com.igdev.secondhand.ui.MainFragment
import com.igdev.secondhand.ui.MainFragmentDirections
import com.igdev.secondhand.ui.viewmodel.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel : AccountViewModel by viewModels()
    private var token : String =""
    private var dataUser : UserLogin?=null

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

        val progressDialog = ProgressDialog(requireContext())

        viewModel.getToken()
        viewModel.getToken.observe(viewLifecycleOwner){
            if (it.token == "def_token"){
                binding.notLogin.visibility = View.VISIBLE
                binding.menuContainer.visibility = View.GONE
            }
            else {
                binding.notLogin.visibility = View.GONE
                binding.menuContainer.visibility = View.VISIBLE
                viewModel.getUserData(it.token)
                token = it.token
            }
        }
        viewModel.user.observe(viewLifecycleOwner){
            when (it.status){
                Status.SUCCESS->{
                    val city = it.data?.city
                    val address = it.data?.address
                    val image = it.data?.imageUrl
                    val password = it.data?.password
                    val phoneNumber = it.data?.phoneNumber
                    val name = it.data?.fullName
                    val email = it.data?.email
                    if (image != null){
                        Glide.with(binding.root)
                            .load(image)
                            .placeholder(R.drawable.ic_profie)
                            .transform(CircleCrop())
                            .into(binding.ivProfile)
                    }
                    binding.tvLocation.text = it.data?.city
                    binding.tvNoWhatsapp.text = it.data?.phoneNumber
                    binding.tvNama.text = name
                    dataUser = UserLogin(name,email, password, phoneNumber, address, city, image, token)
                    progressDialog.dismiss()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                }
                Status.LOADING -> {
                    progressDialog.setMessage("Please Wait...")
                    progressDialog.show()
                }

            }
        }
        binding.bgSectionEdit.setOnClickListener {
            val data = dataUser
            val direct = MainFragmentDirections.actionMainFragmentToEditAccountFragment(data)
            findNavController().navigate(direct)
        }
        binding.bgSectionLogout.setOnClickListener {
            viewModel.deleteToken()
            findNavController().navigate(R.id.action_mainFragment_to_splashFragment)
        }
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }
        binding.bgSectionSetting.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_settingFragment)
        }
        binding.bgSectionWishlist.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_wishlistFragment)
        }
    }

    }