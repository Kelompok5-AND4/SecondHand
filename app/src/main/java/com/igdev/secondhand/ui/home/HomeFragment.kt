package com.igdev.secondhand.ui.home

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentHomeBinding
import com.igdev.secondhand.model.AllProductResponse
import com.igdev.secondhand.model.CategoryResponseItem
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.model.sellerproduct.SellerProductResponseItem
import com.igdev.secondhand.ui.MainFragment
import com.igdev.secondhand.ui.MainFragmentDirections
import com.igdev.secondhand.ui.adapter.BannerAdapter
import com.igdev.secondhand.ui.adapter.CategoryAdapter
import com.igdev.secondhand.ui.adapter.MiniCategoryAdapter
import com.igdev.secondhand.ui.adapter.ProductAdapter
import com.igdev.secondhand.ui.home.paging.ProductPagingAdapter
import com.igdev.secondhand.ui.transaction.SellerAdapter
import com.igdev.secondhand.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel:HomeViewModel by viewModels()
    private val viewModels:PagingViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter
    //private lateinit var productPagingAdapter: ProductPagingAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var miniCategoryAdapter: MiniCategoryAdapter
    private val tokenLelang = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImN1YW43N0BtYWlsLmNvbSIsImlhdCI6MTY1NzU0NDczOH0.OttWesAu57GikyJRZWVXvzXtGtJKzfTnu8MKt5ZEFAc"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainFragment.currentPage = R.id.homeFragment

        setupRecyclerView()
        loadData()

        val viewPager2 = binding.viewPagerBanner
        val handler = Handler(Looper.myLooper()!!)
        val imageList = ArrayList<Int>()
        imageList.add(R.drawable.bannercuan_1)
        imageList.add(R.drawable.bannercuan_2)
        imageList.add(R.drawable.bannercuan_3)
        imageList.add(R.drawable.bannercuan_4)


        val adapter = BannerAdapter(imageList,viewPager2)
        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 2
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val runnable = Runnable {
            viewPager2.currentItem = viewPager2.currentItem + 1
        }

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable,5000)
            }
        })

        setUpTransformer()
        binding.searchView.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
        binding.searchIcon.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
        productAdapter = ProductAdapter(object : ProductAdapter.OnClickListener{
            override fun onClickItem(data: AllProductResponse) {
                val id = data.id
                val toDetail = MainFragmentDirections.actionMainFragmentToDetailProductFragment(id)
                findNavController().navigate(toDetail)
            }
        })


        setUpPaging(pagingData = viewModels.getProducts())
        categoryAdapter = CategoryAdapter(object : CategoryAdapter.OnClickListener{
            override fun onClickItem(data: CategoryResponseItem) {
                val toCategory =
                    MainFragmentDirections.actionMainFragmentToCategoryFragment(data.id)
                findNavController().navigate(toCategory)
            }
        })
        binding.rvCategory.adapter = categoryAdapter
        binding.btnJual.setOnClickListener {
            MainFragment.currentPage = R.id.sellFragment
            findNavController().navigate(R.id.mainFragment)
        }

        miniCategoryAdapter = MiniCategoryAdapter(object : MiniCategoryAdapter.OnClickListener{
            override fun onClickItem(data: CategoryResponseItem) {
                //getProduct(data.id.toString())
                viewModels.getProducts(categoryId = data.id)
            }
        })
        binding.rvMiniCategory.adapter = miniCategoryAdapter
        getCategory()
        binding.ivNotification.setOnClickListener {
            MainFragment.currentPage = R.id.notificationFragment
            findNavController().navigate(R.id.mainFragment)
        }
        binding.tvSeeAll.setOnClickListener {
            val direct = MainFragmentDirections.actionMainFragmentToLelangFragment()
            findNavController().navigate(direct)
        }
        viewModel.getSellerProduct(tokenLelang)
        viewModel.getAllSellerProduct.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        if (it.data.isNullOrEmpty()) {
                            binding.rvSpecialOffer.visibility = View.GONE
                        } else {
                            val sellerAdapter =
                                SellerAdapter(object : SellerAdapter.OnClickListener {
                                    override fun onClickItem(data: SellerProductResponseItem) {
                                        val id = data.id
                                        val toDetail =
                                            MainFragmentDirections.actionMainFragmentToDetailProductFragment(
                                                id
                                            )
                                        findNavController().navigate(toDetail)
                                    }
                                })
                            sellerAdapter.submitData(it.data)
                            binding.rvSpecialOffer.adapter = sellerAdapter
                        }
                    }
                    Status.ERROR -> {
                        AlertDialog.Builder(requireContext())
                            .setMessage(it.message)
                            .show()
                    }
                }
            }
        }
    }

    private fun setUpPaging(pagingData: Flow<PagingData<UiModel.ProductItem>>) {
        val productPagingAdapter = ProductPagingAdapter{
            val direct = MainFragmentDirections.actionMainFragmentToDetailProductFragment(it.id)
            findNavController().navigate(direct)
        }
        lifecycleScope.launchWhenCreated {
            productPagingAdapter.loadStateFlow.collectLatest {

            }
        }
        binding.rvProduct.adapter = productPagingAdapter

    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer{ page, position ->
            val r = 1 - abs (position)
            page.scaleY = 0.85f + r * 0.14f
        }
        binding.viewPagerBanner.setPageTransformer(transformer)
    }

    private fun getCategory(){
        viewModel.getCategory()
        viewModel.category.observe(viewLifecycleOwner){ resources ->
            if (resources.status == Status.SUCCESS){
                categoryAdapter.submitData(resources.data)
                miniCategoryAdapter.submitData(resources.data)
            }
        }
    }

    /*private fun getProduct(categoryId: String) {
        val status = "available"
        val progressDialog = ProgressDialog(requireContext())
        viewModel.getProduct(status, categoryId)
        viewModel.product.observe(viewLifecycleOwner){ resources ->
            when(resources.status){
                Status.LOADING ->{
                    progressDialog.setMessage("Loading")
                    progressDialog.show()
                }
                Status.SUCCESS ->{

                    when(resources.data?.code()){
                        200 ->{
                            if (resources.data.body()?.size!! > 1){
                                productAdapter.submitData(resources.data.body()!!.subList(0,10))
                                progressDialog.dismiss()
                            }
                        }
                    }

                    *//*productAdapter.submitData(resources.data)
                    progressDialog.dismiss()*//*

                }
                Status.ERROR ->{
                    Toast.makeText(requireContext(), resources.message, Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                }
            }
        }
    }*/

    private fun setupRecyclerView() {



    }

    private fun loadData() {


    }
}