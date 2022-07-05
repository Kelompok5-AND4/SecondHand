package com.igdev.secondhand.ui.sellproduct

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.igdev.secondhand.databinding.CategoryBottomSheetBinding
import com.igdev.secondhand.listCategory
import com.igdev.secondhand.listCategoryId
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.ui.viewmodel.SellViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetCategory(private val submit: ()-> Unit):BottomSheetDialogFragment(){
    private var _binding : CategoryBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SellViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CategoryBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please Wait...")
        binding.btnKirimCategory.setOnClickListener {
            progressDialog.show()
            viewModel.addCategory(listCategory)
            Handler().postDelayed({
                progressDialog.dismiss()
                submit.invoke()
                dismiss()
            }, 1000)
        }
        viewModel.getCategory()
        viewModel.category.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    progressDialog.dismiss()
                    if(it.data != null){
                        val adapter = BottomSheetCategoryAdapter(
                            selected = { selected ->
                                listCategory.add(selected.name)
                                listCategoryId.add(selected.id)
                            },
                            unselected = { unselected ->
                                listCategory.remove(unselected.name)
                                listCategoryId.remove(unselected.id)
                            }
                        )
                        adapter.submitData(it.data)
                        binding.rvPilihKategori.adapter = adapter
                    }
                }
                Status.ERROR -> {
                    progressDialog.dismiss()
                    AlertDialog.Builder(requireContext())
                        .setMessage(it.message)
                        .setPositiveButton("Ok"){ dialog, _ ->
                            dialog.dismiss()
                            findNavController().popBackStack()
                        }
                        .show()
                }
                Status.LOADING -> {
                    progressDialog.show()
                }
            }
        }
    }
}