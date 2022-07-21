package com.igdev.secondhand.ui.allfeature

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.igdev.secondhand.R
import com.igdev.secondhand.databinding.FragmentAllFeatureBinding
import com.igdev.secondhand.databinding.FragmentLoginBinding
import com.igdev.secondhand.model.CategoryResponseItem
import com.igdev.secondhand.model.Status
import com.igdev.secondhand.ui.adapter.BannerAdapter
import com.igdev.secondhand.ui.adapter.CategoryAdapter
import com.igdev.secondhand.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class AllFeatureFragment : Fragment() {
    private var _binding: FragmentAllFeatureBinding? = null
    private val binding get() = _binding!!
    private val viewModel:AllFeatureViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllFeatureBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager2 = binding.viewPager
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
        setUpTransformer()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.getCategory()
        viewModel.category.observe(viewLifecycleOwner){
            if (it.status == Status.SUCCESS){
                val categoryAdapter = CategoryAdapter(object : CategoryAdapter.OnClickListener{
                    override fun onClickItem(data: CategoryResponseItem) {
                        val direct = AllFeatureFragmentDirections.actionAllFeatureFragmentToCategoryFragment(data.id)
                        findNavController().navigate(direct)
                    }
                })
                categoryAdapter.submitData(it.data)
                binding.rvFeature.adapter = categoryAdapter
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
        binding.viewPager.setPageTransformer(transformer)
    }



}