package com.igdev.secondhand

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.igdev.secondhand.databinding.FragmentDebugBinding


class DebugFragment : Fragment() {
    private var _bindig : FragmentDebugBinding? = null
    private val binding get() = _bindig!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bindig = FragmentDebugBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnSearchbar.setOnClickListener{
            findNavController().navigate(R.id.action_debugFragment_to_searchFragment)
        }

        binding.btnLelang.setOnClickListener {
            findNavController().navigate(R.id.action_debugFragment_to_lelangFragment)
        }
        val image = ImageView(requireContext())
        image.setImageResource(R.drawable.default_rv)
        binding.tesSnackbar.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Report")
                .setView(image)
                .setMessage("apakah anda merasa ini merupakan barang illegal?")
                .setPositiveButton("Iya"){ p1,p2->
                    Toast.makeText(requireContext(),"Lanjut ke WA",1000).show()
                    p1.dismiss()
                }
                .setNegativeButton("Batal"){p1,p2->
                    Toast.makeText(requireContext(),"Batal",1000).show()
                    p1.dismiss()
                }.setCancelable(false).show()
        }
    }
}
