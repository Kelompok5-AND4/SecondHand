package com.igdev.secondhand

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.igdev.secondhand.databinding.FragmentAboutUsBinding


class AboutUsFragment : Fragment() {

    private var _binding: FragmentAboutUsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.linkedinSatu.setOnClickListener {
            val uri: Uri =
                Uri.parse("https://www.linkedin.com/in/muhammad-khoirul-anwar-b45897163/") // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        binding.linkedinDua.setOnClickListener {
            val uri: Uri =
                Uri.parse("https://www.linkedin.com/in/maulida08/") // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        binding.linkedinTiga.setOnClickListener {
            val uri: Uri =
                Uri.parse("https://www.linkedin.com/in/rizqifakhri/") // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        binding.linkedinEmpat.setOnClickListener {
            val uri: Uri =
                Uri.parse("https://www.linkedin.com/in/afifuddin-s-1b634b20b/") // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }

}