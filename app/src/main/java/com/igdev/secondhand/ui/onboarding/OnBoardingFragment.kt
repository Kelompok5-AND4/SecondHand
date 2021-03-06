package com.igdev.secondhand.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentHomeBinding
import com.igdev.secondhand.databinding.FragmentOnBoardingBinding
import com.igdev.secondhand.ui.onboarding.screens.FirstFragment
import com.igdev.secondhand.ui.onboarding.screens.FourthScreen
import com.igdev.secondhand.ui.onboarding.screens.SecondFragment
import com.igdev.secondhand.ui.onboarding.screens.ThirdFragment

class OnBoardingFragment : Fragment() {
    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        val fragmentList = arrayListOf<Fragment>(
            FirstFragment(),
            SecondFragment(),
            ThirdFragment(),
            FourthScreen()
        )

        val adapter = OnBoardingAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        val viewPager = binding.viewPager
        binding.viewPager.adapter = adapter
        /*val tabLayout = binding.intoTabLayout
        TabLayoutMediator(tabLayout,viewPager){
            tab,position-> }.attach()*/
        return binding.root
    }

}