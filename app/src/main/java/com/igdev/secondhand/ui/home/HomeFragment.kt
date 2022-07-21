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
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
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
import com.igdev.secondhand.ui.adapter.*
import com.igdev.secondhand.ui.home.paging.ProductLoadStateAdapter
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
    private val viewModel: HomeViewModel by viewModels()
    private val viewModels: PagingViewModel by viewModels()
    private lateinit var miniCategoryAdapter: MiniCategoryAdapter
    var category: Int? = null
    private val tokenLelang =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImN1YW44OEBtYWlsLmNvbSIsImlhdCI6MTY1ODQwODM0Nn0.nmyGkOxQgrR39r44TP2RmhY8R9jMn6g4u57gh5GaDtQ"
    var topCategory = 0
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


        val viewPager2 = binding.viewPagerBanner
        val handler = Handler(Looper.myLooper()!!)
        val imageList = ArrayList<Int>()
        imageList.add(R.drawable.bannercuan_1)
        imageList.add(R.drawable.bannercuan_2)
        imageList.add(R.drawable.bannercuan_3)
        imageList.add(R.drawable.bannercuan_4)


        val adapter = BannerAdapter(imageList, viewPager2)
        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 2
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val runnable = Runnable {
            viewPager2.currentItem = viewPager2.currentItem + 1
        }

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 5000)
            }
        })

        setUpCategory()

        setUpTransformer()
        binding.searchView.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
        binding.searchIcon.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
        val productPagingAdapter = ProductPagingAdapter {
            val direct = MainFragmentDirections.actionMainFragmentToDetailProductFragment(it.id)
            findNavController().navigate(direct)
        }
        val productLoadStateAdapter = ProductLoadStateAdapter { productPagingAdapter.retry() }
        setUpPaging(
            adapter = productPagingAdapter,
            load = productLoadStateAdapter,
            pagingData = viewModels.getProducts()
        )

        binding.btnJual.setOnClickListener {
            MainFragment.currentPage = R.id.sellFragment
            findNavController().navigate(R.id.mainFragment)
        }

        miniCategoryAdapter = MiniCategoryAdapter(object : MiniCategoryAdapter.OnClickListener {
            override fun onClickItem(data: CategoryResponseItem, position: Int) {
                //getProduct(data.id.toString())
                if (position == 0) {
                    category = null
                    setUpPaging(
                        productPagingAdapter,
                        productLoadStateAdapter,
                        viewModels.getProducts()
                    )
                } else {
                    category = data.id
                    val kategoriId = category
                    setUpPaging(
                        productPagingAdapter,
                        productLoadStateAdapter,
                        viewModels.getProducts(kategoriId)
                    )
                }

            }
        })
        val kategoriId = category
        binding.swipe.setOnRefreshListener {
            setUpPaging(
                productPagingAdapter,
                productLoadStateAdapter,
                viewModels.getProducts(kategoriId)
            )
        }
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
                                 LelangAdapter(object : LelangAdapter.OnClickListener {
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
                        binding.bgLelang.visibility =View.GONE
                        binding.specialOffer.visibility =View.GONE
                    }
                }
            }
        }
        binding.swipe.setOnRefreshListener {
            setUpPaging(
                productPagingAdapter,
                productLoadStateAdapter,
                viewModels.getProducts(kategoriId)
            )
        }
    }

    private fun setUpCategory() {

        binding.icElektronik.setOnClickListener {
            topCategory = 1
            val direction = MainFragmentDirections.actionMainFragmentToCategoryFragment(topCategory)
            findNavController().navigate(direction)
        }
        binding.icOtomotif.setOnClickListener {
            topCategory = 19
            val direction = MainFragmentDirections.actionMainFragmentToCategoryFragment(topCategory)
            findNavController().navigate(direction)
        }
        binding.icKesehatan.setOnClickListener {
            topCategory = 8
            val direction = MainFragmentDirections.actionMainFragmentToCategoryFragment(topCategory)
            findNavController().navigate(direction)
        }
        binding.icPhotography.setOnClickListener {
            topCategory = 24
            val direction = MainFragmentDirections.actionMainFragmentToCategoryFragment(topCategory)
            findNavController().navigate(direction)
        }
        binding.icVoucher.setOnClickListener {
            topCategory = 22
            val direction = MainFragmentDirections.actionMainFragmentToCategoryFragment(topCategory)
            findNavController().navigate(direction)
        }
        binding.icAll.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_allFeatureFragment)
        }

    }

    private fun setUpPaging(
        adapter: ProductPagingAdapter,
        load: ProductLoadStateAdapter,
        pagingData: Flow<PagingData<UiModel.ProductItem>>
    ) {
        val footerAdapter = ProductLoadStateAdapter { adapter.retry() }
        binding.rvProduct.adapter = adapter.withLoadStateHeaderAndFooter(
            header = load,
            footer = footerAdapter
        )

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest {
                binding.swipe.isRefreshing = it.refresh is LoadState.Loading
            }
        }
        val gridLayoutManager = binding.rvProduct.layoutManager as GridLayoutManager
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if ((position == adapter.itemCount && footerAdapter.itemCount > 0) ||
                    (position == adapter.itemCount && load.itemCount > 0)
                ) {
                    2
                } else {
                    1
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            pagingData.collectLatest(adapter::submitData)
        }
        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->
                load.loadState = loadState.mediator
                    ?.refresh
                    ?.takeIf { it is LoadState.Error && adapter.itemCount > 0 }
                    ?: loadState.prepend
                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
                binding.rvProduct.isVisible =
                    loadState.source.refresh is LoadState.NotLoading
                            || loadState.mediator?.refresh is LoadState.NotLoading
                binding.pbHomeProduct.isVisible = loadState.mediator?.refresh is LoadState.Loading
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        context,
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        binding.viewPagerBanner.setPageTransformer(transformer)
    }

    private fun getCategory() {
        val progressDialog = ProgressDialog(requireContext())
        viewModel.getCategory()
        viewModel.category.observe(viewLifecycleOwner) { resources ->
            when (resources.status) {
                Status.LOADING -> {
                    progressDialog.setMessage("Loading")
                    progressDialog.show()
                }
                Status.SUCCESS -> {
                    miniCategoryAdapter.submitData(resources.data)
                    progressDialog.dismiss()

                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), resources.message, Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                }
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

}