package com.igdev.secondhand.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentNotificationBinding
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.notification.NotifResponseItem
import com.igdev.secondhand.ui.adapter.NotificationAdapter
import com.igdev.secondhand.ui.viewmodel.NotifViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private val viewModel : NotifViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNotificationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        MainFragment.currentPage = R.id.notificationFragment
        viewModel.getToken()
        viewModel.getToken.observe(viewLifecycleOwner){
            if (it.token=="def_token"){
                binding.notLogin.visibility =View.VISIBLE
                binding.emptyNotif.visibility = View.GONE
                binding.rvNotif.visibility = View.GONE
            }else{
                viewModel.getAllNotif(it.token)
                binding.notLogin.visibility = View.GONE
            }
        }
        viewModel.getNotif.observe(viewLifecycleOwner){
            item ->
            when(item.status){
                Status.LOADING ->{
                    binding.pbLoading.visibility = View.VISIBLE
                }
                Status.SUCCESS ->{
                    if (item.data.isNullOrEmpty()){
                       binding.emptyNotif.visibility = View.VISIBLE
                    }
                    else{
                        val adapter = NotificationAdapter(object :NotificationAdapter.onClickListener{
                            override fun onClickItem(data: NotifResponseItem) {
                            }
                        })
                        adapter.submitData(item.data)
                        binding.rvNotif.adapter = adapter
                    }
                    binding.pbLoading.visibility = View.GONE
                }
                Status.ERROR ->{
                 binding.pbLoading.visibility = View.GONE
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }
    }



}