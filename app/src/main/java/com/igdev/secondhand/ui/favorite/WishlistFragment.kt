package com.igdev.secondhand.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.igdev.secondhand.databinding.FragmentWishlistBinding


class WishlistFragment : Fragment() {
    private var _binding : FragmentWishlistBinding? = null
    private val binding get() = _binding!!
    private val viewModel : WishlistViewModel by viewModels()
    private lateinit var adapter : WishlistAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWishlistBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}