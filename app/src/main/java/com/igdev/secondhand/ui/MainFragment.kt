package com.igdev.secondhand.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentMainBinding
import com.igdev.secondhand.ui.sellproduct.SellFragment
import com.igdev.secondhand.ui.transaction.TransactionFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    companion object {
        var currentPage = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.menuNavigation.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.homeFragment -> {

                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_host, HomeFragment())
                        ?.commit()

                    true
                }

                R.id.notificationFragment -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_host, NotificationFragment())
                        ?.commit()

                    true
                }

                R.id.sellFragment -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_host, SellFragment())
                        ?.commit()

                    true
                }

                R.id.transactionFragment -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_host, TransactionFragment())
                        ?.commit()

                    true
                }

                R.id.accountFragment -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_host, AccountFragment())
                        ?.commit()

                    true
                }

                else -> false
            }
        }

        binding.menuNavigation.selectedItemId = if (currentPage == 0) {
            R.id.homeFragment
        } else {
            currentPage
        }

    }
}