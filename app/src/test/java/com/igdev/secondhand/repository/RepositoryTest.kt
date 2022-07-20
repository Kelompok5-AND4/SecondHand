package com.igdev.secondhand.repository

import androidx.paging.PagingData
import com.igdev.secondhand.database.DbHelper
import com.igdev.secondhand.database.MyDatabase
import com.igdev.secondhand.database.SearchHistoryEntity
import com.igdev.secondhand.datastore.DataStore
import com.igdev.secondhand.model.AllProductResponse
import com.igdev.secondhand.model.CategoryResponseItem
import com.igdev.secondhand.model.PagingProduct.Product
import com.igdev.secondhand.model.UpdateResponse
import com.igdev.secondhand.model.User
import com.igdev.secondhand.model.addProduct.SellProductResponse
import com.igdev.secondhand.model.buyerorder.BuyerOrderResponse
import com.igdev.secondhand.model.detail.GetDetail
import com.igdev.secondhand.model.detail.PostOrderReq
import com.igdev.secondhand.model.detail.PostOrderResponse
import com.igdev.secondhand.model.getAuth.ResponseAuth
import com.igdev.secondhand.model.login.LoginReq
import com.igdev.secondhand.model.login.LoginResponse
import com.igdev.secondhand.model.notification.NotifResponseItem
import com.igdev.secondhand.model.register.RegistReq
import com.igdev.secondhand.model.register.RegisterResponse
import com.igdev.secondhand.model.sellerorder.SellerOrderResponseItem
import com.igdev.secondhand.model.sellerproduct.SellerProductDetail
import com.igdev.secondhand.model.sellerproduct.SellerProductResponseItem
import com.igdev.secondhand.model.settingaccount.SettingAccountRequest
import com.igdev.secondhand.model.settingaccount.SettingAccountResponse
import com.igdev.secondhand.model.wishlist.DeleteWishlistResponse
import com.igdev.secondhand.model.wishlist.GetWishlistResponse
import com.igdev.secondhand.model.wishlist.PostWishListResponse
import com.igdev.secondhand.model.wishlist.PostWishlistRequest
import com.igdev.secondhand.service.ApiHelper
import com.igdev.secondhand.service.ApiService
import com.igdev.secondhand.ui.viewmodel.RegisterViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Test
import org.mockito.kotlin.mock
import retrofit2.Response


class RepositoryTest {
    private val reqPostRegister = RegistReq("aaaa@gmail.com", "123456")
    private val reqLoginPost = LoginReq("fudinafif56@gmail.com", "123456")
    private val user = User("lfjhsdafhsjadhfjkasd")
    private val token = "jkfhajskdhfjksdhfkldfkhuerh"

    //collaborator
    private lateinit var apiService: ApiService
    private lateinit var apiHelper: ApiHelper
    private lateinit var dbHelper: DbHelper
    private lateinit var dataStore: DataStore
    private lateinit var dB: MyDatabase

    //
    private lateinit var repository: Repository
    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        apiService = mockk()
        dataStore = mockk()
        apiHelper = ApiHelper(apiService)
        dbHelper = mockk()
        dB = mockk()
        repository = Repository(apiHelper, dataStore, dbHelper, dB)

    }

    @Test
    fun postRegUser(): Unit = runBlocking {

        //given
        val regUser = mockk<RegisterResponse>()
        every {
            runBlocking {
                apiService.postRegUser(reqPostRegister)
            }
        } returns regUser

        //when
        repository.postRegUser(reqPostRegister)

        //try
        verify {
            runBlocking {
                apiService.postRegUser(reqPostRegister)
            }
        }
    }

    @Test
    fun postLogin(): Unit = runBlocking {
        //given
        val postLogin = mockk<LoginResponse>()
        every {
            runBlocking {
                apiService.postLogin(reqLoginPost)
            }
        } returns postLogin

        //when
        repository.postLogin(reqLoginPost)

        //try
        verify {
            runBlocking {
                apiService.postLogin(reqLoginPost)
            }
        }
    }

    @Test
    fun getAllProduct(): Unit = runBlocking {
        //given
        val getAllProduct = mockk<Response<List<AllProductResponse>>>()
        every {
            runBlocking {
                apiService.getAllProduct()
            }
        } returns getAllProduct

        //when
        repository.getAllProduct()

        //try
        verify {
            runBlocking {
                apiService.getAllProduct()
            }
        }
    }

    @Test
    fun getAllCategory(): Unit = runBlocking {

        val getAllCategory = mockk<List<CategoryResponseItem>>()
        every {
            runBlocking {
                apiService.getAllCategory()
            }
        } returns getAllCategory

        repository.getAllCategory()

        verify {
            runBlocking {
                apiService.getAllCategory()
            }
        }

    }

    @Test
    fun setDatastore(): Unit = runBlocking {
        val setDatastore = mockk<Unit>()
        every {
            runBlocking {
                dataStore.setToken(user)
            }
        } returns setDatastore

        repository.setDatastore(user)

        verify {
            runBlocking {
                dataStore.setToken(user)
            }
        }

    }

    @Test
    fun getDatastore() : Unit = runBlocking {
        val getDatastore = mockk<Flow<User>>()

        every {
            runBlocking {
                dataStore.getToken()
            }
        }returns getDatastore

        repository.getDatastore()

        verify {
            runBlocking {
                dataStore.getToken()
            }
        }
    }

    @Test
    fun deleteToken() : Unit = runBlocking {
        val deleteToken = mockk<Unit>()
        every {
            runBlocking {
                dataStore.delete()
            }
        }returns deleteToken

        repository.deleteToken()

        verify {
            runBlocking {
                dataStore.delete()
            }
        }

    }

    @Test
    fun getDataUser() : Unit = runBlocking {
        val getDataUser = mockk<ResponseAuth>()
        every {
            runBlocking {
                apiService.getDataUser(token)
            }
        }returns getDataUser

        repository.getDataUser(token)

        verify {
            runBlocking{
                apiService.getDataUser(token)
            }
        }
    }

    @Test
    fun updateDataUser() : Unit = runBlocking {
        val updateDataUser = mockk<UpdateResponse>()
        every {
            runBlocking {
                apiService.updateDataUser(token,null,null,null,null,null,null)
            }
        }returns updateDataUser

        repository.updateDataUser(token,null,null,null,null,null)

        verify {
            runBlocking {
                apiService.updateDataUser(token, null, null,null,null,null,null)
            }
        }
    }

    @Test
    fun getNotif() : Unit = runBlocking {
        val getNotif = mockk<List<NotifResponseItem>>()
        every {
            runBlocking {
                apiService.GetNotif(token)
            }
        }returns getNotif

        repository.getNotif(token)

        verify {
            runBlocking {
                apiService.GetNotif(token)
            }
        }
    }

    @Test
    fun getDetail() : Unit = runBlocking {
        val getDetail = mockk<GetDetail>()
        every {
            runBlocking {
                apiService.getIdProduct(2)
            }
        }returns getDetail

        repository.getDetail(2)

        verify {
            runBlocking {
                apiService.getIdProduct(2)
            }
        }
    }

    @Test
    fun getSellerDetailProduct() : Unit = runBlocking {
        val getSellerDetail = mockk<SellerProductDetail>()
        every {
            runBlocking {
                apiService.getSellerProductDetail(token,2)
            }
        }returns getSellerDetail

        repository.getSellerDetailProduct(token,2)

        verify {
            runBlocking {
                apiService.getSellerProductDetail(token,2)
            }
        }
    }

    @Test
    fun postProduct() : Unit = runBlocking{
        val postProduct = mockk<SellProductResponse>()
        every {
            runBlocking {
                apiService.postProduct(token,null,null,null,null,null,null)
            }
        }returns postProduct
        repository.postProduct(token,null,null,null,null,null,null)

        verify {
            runBlocking {
                apiService.postProduct(token,null,null,null,null,null,null)
            }
        }

    }

    @Test
    fun getBuyerHistory() : Unit = runBlocking{
        val getBuyerHistory = mockk< List<BuyerOrderResponse>>()
        every {
            runBlocking {
                apiService.getBuyerHistory(token)
            }
        }returns getBuyerHistory

        repository.getBuyerHistory(token)
        verify {
            runBlocking {
                apiService.getBuyerHistory(token)
            }
        }
    }

    @Test
    fun getSellerProduct() : Unit = runBlocking {
        val getSellerProduct = mockk<List<SellerProductResponseItem>>()
        every {
            runBlocking {
                apiService.getSellerProduct(token)
            }
        }returns getSellerProduct

        repository.getSellerProduct(token)

        verify {
            runBlocking {
                apiService.getSellerProduct(token)
            }
        }
    }

    @Test
    fun getSellerOrder() : Unit = runBlocking {
        val getSellerOrder = mockk<List<SellerOrderResponseItem>>()
        every {
            runBlocking {
                apiService.getSellerOrder(token)
            }
        }returns getSellerOrder

        repository.getSellerOrder(token)

        verify {
            runBlocking {
                apiService.getSellerOrder(token)
            }
        }
    }

    @Test
    fun postOrder() : Unit = runBlocking {
        val postOder = mockk<Response<PostOrderResponse>>()
        val reqPostOrder = PostOrderReq("3","200000")
        every {
            runBlocking {
                apiService.postBuyerOrder(token,reqPostOrder)
            }
        }returns postOder

        repository.postOrder(token,reqPostOrder)
        verify {
            runBlocking {
                apiService.postBuyerOrder(token,reqPostOrder)
            }
        }
    }

    @Test
    fun getSearchHistory() : Unit = runBlocking {
        val getSerchHistory = mockk<List<SearchHistoryEntity>>()
        every {
            runBlocking {
                dbHelper.getSearchHistory()
            }
        }returns getSerchHistory

        repository.getSearchHistory()
        verify {
            runBlocking {
                dbHelper.getSearchHistory()
            }
        }
    }

    @Test
    fun insertSearchHistory() :Unit = runBlocking {
        val insertSearchHistory = 1L
        val searchHisEntity = SearchHistoryEntity(5,"phone")
        every {
            runBlocking {
                dbHelper.insertSearchHistory(searchHisEntity)
            }
        }returns insertSearchHistory
        repository.insertSearchHistory(searchHisEntity)

        verify {
            runBlocking {
                dbHelper.insertSearchHistory(searchHisEntity)
            }
        }
    }



    @Test
    fun settingAccount() : Unit = runBlocking {
        val settingAccount = mockk<SettingAccountResponse>()
        val reqSettingAccount = SettingAccountRequest("hjkdfhgkjdf","gfghsdhdfh","gfghsdhdfh")
        every {
            runBlocking {
                apiService.updatePassword(token,reqSettingAccount)
            }
        }returns settingAccount
        repository.settingAccount(token,reqSettingAccount)
        verify {
            runBlocking {
                apiService.updatePassword(token,reqSettingAccount)
            }
        }
    }

    @Test
    fun getWishlist() : Unit = runBlocking {
        val getWishlist = mockk<List<GetWishlistResponse>>()
        every {
            runBlocking {
                apiService.getWishlist(token)
            }
        }returns getWishlist

        repository.getWishlist(token)

        verify {
            runBlocking {
                apiService.getWishlist(token)
            }
        }
    }



    @Test
    fun postWishlist() : Unit = runBlocking {
        val postWishlist = mockk<PostWishListResponse>()
        val postWishlistRequest = PostWishlistRequest(2)
        every {
            runBlocking {
                apiService.postWishlist(token,postWishlistRequest)
            }
        }returns postWishlist
        repository.postWishlist(token,postWishlistRequest)
        verify {
            runBlocking {
                apiService.postWishlist(token,postWishlistRequest)
            }
        }
    }

    @Test
    fun deleteWishlist() : Unit = runBlocking {
        val deleteWishlist = mockk<DeleteWishlistResponse>()
        every {
            runBlocking {
                apiService.deleteWishlist(token,2)
            }
        }returns deleteWishlist

        repository.deleteWishlist(token,2)

        verify {
            runBlocking {
                apiService.deleteWishlist(token,2)
            }
        }
    }


}