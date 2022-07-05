package com.igdev.secondhand.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igdev.secondhand.model.CategoryResponseItem
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.model.User
import com.igdev.secondhand.model.addProduct.SellProductResponse
import com.igdev.secondhand.model.getAuth.ResponseAuth
import com.igdev.secondhand.reduceImageSize
import com.igdev.secondhand.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


@HiltViewModel
class SellViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    private val _postProduct = MutableLiveData<Resource<SellProductResponse>>()
    val postProduct : LiveData<Resource<SellProductResponse>> get() = _postProduct

    private val _getToken = MutableLiveData<User>()
    val getToken: LiveData<User> get() = _getToken

    private var _user = MutableLiveData<Resource<ResponseAuth>>()
    val user : LiveData<Resource<ResponseAuth>> get() = _user
    private var _categoryList = MutableLiveData<List<String>>()
    val categoryList : LiveData<List<String>> get() = _categoryList

    private val _category = MutableLiveData<Resource<List<CategoryResponseItem>>>()
    val category : LiveData<Resource<List<CategoryResponseItem>>> get() = _category

    fun getToken() {
        viewModelScope.launch {
            repository.getDatastore().collect {
                _getToken.postValue(it)
            }
        }
    }

    fun getCategory(){
        viewModelScope.launch {
            _category.postValue(Resource.loading())
            try {
                _category.postValue((Resource.success(repository.getAllCategory())))
            }catch (e:Exception){
                _category.postValue(Resource.error(e.localizedMessage?:"Error Occurred"))
            }
        }
    }
    fun postProduct(
        token: String,
        namaProduk: String,
        deskripsiProduk: String,
        hargaProduk: String,
        kategoriProduk: List<Int>,
        alamatPenjual: String,
        image: File
    ){
        val requestFile = reduceImageSize(image).asRequestBody("image/jpg".toMediaTypeOrNull())
        val gambarProduk = MultipartBody.Part.createFormData("image", image.name, requestFile)
        val namaRequestBody = namaProduk.toRequestBody("text/plain".toMediaType())
        val deskripsiRequestBody = deskripsiProduk.toRequestBody("text/plain".toMediaType())
        val hargaRequestBody = hargaProduk.toRequestBody("text/plain".toMediaType())
        val alamatRequestBody = alamatPenjual.toRequestBody("text/plain".toMediaType())

        viewModelScope.launch {
            _postProduct.postValue(Resource.loading())
            try {
                _postProduct.postValue(Resource.success(repository.postProduct(
                    token,
                    gambarProduk,
                    namaRequestBody,
                    deskripsiRequestBody,
                    hargaRequestBody,
                    kategoriProduk,
                    alamatRequestBody
                )))
            } catch (e: Exception){
                _postProduct.postValue(Resource.error(e.localizedMessage?:"Error occured"))
            }
        }
    }

    fun getUserData(token: String){
        viewModelScope.launch {
            _user.postValue(Resource.loading())
            try {
                _user.postValue(Resource.success(repository.getDataUser(token)))
            } catch (e: Exception){
                _user.postValue(Resource.error(e.localizedMessage?: "Error Occured"))
            }
        }
    }

    fun addCategory(category: List<String>){
        _categoryList.postValue(category)
    }

}