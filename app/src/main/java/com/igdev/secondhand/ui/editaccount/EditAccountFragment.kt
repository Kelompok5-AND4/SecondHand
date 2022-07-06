package com.igdev.secondhand.ui.editaccount

import android.app.Activity
import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.github.dhaval2404.imagepicker.ImagePicker
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentEditAccountBinding
import com.igdev.secondhand.ui.viewmodel.EditAccountViewModel
import com.igdev.secondhand.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class EditAccountFragment : Fragment() {
    private var _binding: FragmentEditAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel : EditAccountViewModel by viewModels()
    private var uri: String = ""
    private var imageFile: File? = null
    private val args:EditAccountFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        binding.apply {
            etUsername.setText(args.user?.username)
            etCity.setText(args.user?.city)
            etAddress.setText(args.user?.address)
            etPhoneNumber.setText(args.user?.phoneNumber)
            Glide.with(binding.root).load(args.user?.image ?: R.drawable.ic_profie).transform(CircleCrop()).into(ivUser)
            ivUser.setOnClickListener {
                openImagePicker()
            }
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnUpdate.setOnClickListener {
                val nama = binding.etUsername.text.toString()
                val kota = binding.etCity.text.toString()
                val alamat = binding.etAddress.text.toString()
                val noHp = binding.etPhoneNumber.text.toString()
                val token = args.user?.token
                if (token != null) {
                    viewModel.updateDataUser(
                        token,
                        uriToFile(Uri.parse(uri), requireContext()),
                        nama,
                        noHp,
                        alamat,
                        kota
                    )
                    progressDialog.show()
                    Handler().postDelayed({
                        progressDialog.dismiss()
                        findNavController().popBackStack()
                        Toast.makeText(requireContext(), "Berhasil Update Data!", Toast.LENGTH_SHORT)
                            .show()
                    }, 1500)
                }
            }
        }
    }
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data
                    uri = fileUri.toString()
                    if (fileUri != null) {
                        imageFile = uriToFile(fileUri, requireContext())
                        loadImage(fileUri)
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {

                }
            }
        }

    private fun loadImage(uri: Uri) {
        binding.apply {
            Glide.with(binding.root)
                .load(uri)
                .transform(CenterCrop(), RoundedCorners(12))
                .into(ivUser)

        }
    }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop()
            .saveDir(
                File(
                    requireContext().externalCacheDir,
                    "ImagePicker"
                )
            ) //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }
}