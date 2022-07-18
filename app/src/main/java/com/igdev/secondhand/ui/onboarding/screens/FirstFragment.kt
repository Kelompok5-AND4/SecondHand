package com.igdev.secondhand.ui.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentFirstBinding
import com.igdev.secondhand.databinding.FragmentOnBoardingBinding


class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater,container,false)
        val viewPager =  activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.icNext.setOnClickListener {
            viewPager?.currentItem = 1
        }
        return binding.root
    }

}