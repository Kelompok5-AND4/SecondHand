package com.igdev.secondhand.ui.editaccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentEditAccountBinding

class EditAccountFragment : Fragment() {
    private var _binding: FragmentEditAccountBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

}