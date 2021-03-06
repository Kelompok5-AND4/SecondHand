package com.igdev.secondhand.ui.editProduct

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.cottacush.android.currencyedittext.CurrencyInputWatcher
import com.github.dhaval2404.imagepicker.ImagePicker
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentEditProductBinding
import com.igdev.secondhand.databinding.FragmentSellBinding
import com.igdev.secondhand.listCategory
import com.igdev.secondhand.listCategoryId
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.local.ProductPreview
import com.igdev.secondhand.model.local.UserLogin
import com.igdev.secondhand.ui.MainFragment
import com.igdev.secondhand.ui.MainFragmentDirections
import com.igdev.secondhand.ui.sellproduct.BottomSheetCategory
import com.igdev.secondhand.ui.viewmodel.SellViewModel
import com.igdev.secondhand.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.*

@AndroidEntryPoint
class EditProductFragment : Fragment() {

    private var _binding: FragmentEditProductBinding?=null
    private val binding get() = _binding!!
    private val args:EditProductFragmentArgs by navArgs()

    private var uri:String = ""
    private val viewModel: EditProductViewModel by viewModels()
    private var token : String =""
    private var dataUser : UserLogin?=null
    private var dataProduct : ProductPreview?=null
    private var imageFile: File? = null
    private var imageUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentEditProductBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressDialog = ProgressDialog(requireContext())
        viewModel.getToken()
        viewModel.getToken.observe(viewLifecycleOwner){
            if(it.token == "def_token"){
                binding.notLogin.visibility = View.VISIBLE
                binding.formProduct.visibility = View.GONE
                binding.groupButton.visibility = View.GONE
            }else{
                token = it.token
                viewModel.getUserData(token)
                viewModel.getDetail(token,args.id)
            }
        }
        viewModel.detail.observe(viewLifecycleOwner){
            binding.etNamaProduk.setText(it.data?.name)
            binding.etHargaProduk.setText(it.data?.basePrice.toString())
            binding.etDeskripsi.setText(it.data?.description)
            imageUrl = it.data?.imageUrl.toString()
            if (it.data !=null){
                listCategoryId.clear()
                listCategory.clear()
                for (i in it.data.categories){
                    listCategoryId.add(i.id)
                    listCategory.add(i.name)
                }
                var kategori = ""
                for (element in listCategory){
                    kategori += ", $element"
                }
                binding.etCategory.setText(kategori.drop(2))
            }
            Glide.with(binding.root).load(it.data?.imageUrl).placeholder(R.drawable.default_rv).into(binding.addFoto)
        }
        viewModel.user.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS ->{
                    progressDialog.dismiss()
                    if (it.data != null){
                        val city = it.data.city
                        val address = it.data.address
                        val image = it.data.imageUrl
                        val password = it.data.password
                        val phoneNumber = it.data.phoneNumber
                        val name = it.data.fullName
                        val email = it.data.email
                        dataUser = UserLogin(name,email,password,phoneNumber,address, city, image, token)
                    }
                }
                Status.ERROR -> {
                    progressDialog.dismiss()
                    AlertDialog.Builder(requireContext())
                        .setMessage(it.message)
                        .setPositiveButton("Ok") { dialog, _ ->
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
        viewModel.categoryList.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                var kategori = ""
                for (element in it){
                    kategori += ", $element"
                }
                binding.etCategory.setText(kategori.drop(2))
            }
        }

        binding.etCategory.setOnClickListener {
            val bottomFragment = BottomSheetCategory(
                submit = {viewModel.addCategory(listCategory)}
            )
            bottomFragment.show(parentFragmentManager,"Category")
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.addFoto.setOnClickListener {
            openImagePicker()
        }
        binding.btnPreview.setOnClickListener {
            binding.tilNamaProduk.error = null
            binding.tilHargaProduk.error = null
            binding.tilDeskripsi.error = null
            binding.tilKategori.error = null
            val edit = binding.etHargaProduk
            val namaProduk = binding.etNamaProduk.text.toString()
            val hargaProduk = binding.etHargaProduk.getNumericValue().toInt().toString()
            binding.etHargaProduk.addTextChangedListener(CurrencyInputWatcher(edit,"Rp ", Locale.getDefault()))
            val deskripsiProduk = binding.etDeskripsi.text.toString()
            val kategoriProduk = binding.etCategory.text.toString()
            val validation = validasi(
                namaProduk,
                hargaProduk,
                deskripsiProduk,
                listCategoryId
            )
            if (validation == "passed") {
                dataProduct = ProductPreview(namaProduk,hargaProduk,deskripsiProduk,kategoriProduk,uri?:imageUrl)
                val data = dataProduct
                val user = dataUser
                val direct = EditProductFragmentDirections.actionEditProductFragmentToPreviewFragment(data,user)
                findNavController().navigate(direct)
            }
        }
        binding.btnTerbitkan.setOnClickListener {
            binding.tilNamaProduk.error = null
            binding.tilHargaProduk.error = null
            binding.tilDeskripsi.error = null
            binding.tilKategori.error = null
            val edit = binding.etHargaProduk
            val namaProduk = binding.etNamaProduk.text.toString()
            val hargaProduk = binding.etHargaProduk.getNumericValue().toInt().toString()
            binding.etHargaProduk.addTextChangedListener(CurrencyInputWatcher(edit,"Rp ", Locale.getDefault()))
            val deskripsiProduk = binding.etDeskripsi.text.toString()
            val validation = validasi(
                namaProduk,
                hargaProduk,
                deskripsiProduk,
                listCategoryId
            )
            if (validation == "passed") {
                viewModel.editProduct(
                    token,
                    args.id,
                    namaProduk,
                    deskripsiProduk,
                    hargaProduk,
                    listCategoryId,
                    dataUser?.address.toString(),
                    imageFile
                )
            }
        }

        viewModel.editProduct.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    progressDialog.dismiss()
                    Toast.makeText(requireContext(), "Barang Anda berhasil di terbitkan", Toast.LENGTH_SHORT)
                        .show()
                    listCategoryId.clear()
                    MainFragment.currentPage = R.id.transactionFragment
                    findNavController().navigate(R.id.action_editProductFragment_to_mainFragment)
                }
                Status.ERROR -> {
                    progressDialog.dismiss()
                    var message = ""
                    when (it.message) {
                        "HTTP 400 Bad Request" -> {
                            message = "${it.message}"
                        }
                    }
                    AlertDialog.Builder(requireContext())
                        .setTitle("Pesan")
                        .setMessage(message)
                        .setPositiveButton("Iya") { positiveButton, _ ->
                            positiveButton.dismiss()
                        }
                        .show()
                }
                Status.LOADING -> {
                    progressDialog.show()
                }
            }
        }
    }

    fun validasi(namaProduk: String, hargaProduk: String, deskripsiProduk: String,  listCategory: List<Int>): String {
        when {
            namaProduk.isEmpty() -> {
                binding.tilNamaProduk.error = "Nama Produk tidak boleh kosong"
                return "Nama Produk Kosong!"
            }
            hargaProduk.isEmpty() -> {
                binding.tilHargaProduk.error = "Harga Produk tidak boleh kosong"
                return "Harga Produk Kosong!"
            }
            hargaProduk.toInt() > 2000000000 -> {
                binding.tilHargaProduk.error = "Harga Produk tidak boleh lebih dari 2M"
                return "Harga Produk Melebihi Batas!"
            }
            deskripsiProduk.isEmpty() -> {
                binding.tilDeskripsi.error = "Deskripsi Produk tidak boleh kosong"
                return "Deskripsi Produk Kosong!"
            }
            listCategory.isEmpty() -> {
                binding.tilKategori.error = "Kategori produk tidak boleh kosong"
                Toast.makeText(requireContext(), "Kategori Produk Kosong", Toast.LENGTH_SHORT).show()
                return "Kategori Produk Kosong!"
            }
            else -> {
                return "passed"
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
                .into(addFoto)

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
            .compress(1024) //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }
}