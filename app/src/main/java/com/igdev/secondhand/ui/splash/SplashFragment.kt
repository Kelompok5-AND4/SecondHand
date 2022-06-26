package com.igdev.secondhand.ui.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.igdev.secondhand.R
import com.igdev.secondhand.ui.viewmodel.NotifViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val viewModel: NotifViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getToken()
        viewModel.getToken.observe(viewLifecycleOwner){
            if (it.token == "def_token"){
                Handler(Looper.getMainLooper()).postDelayed({
                    if(onBoardingFinished()){
                        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                    }else{
                        findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
                    }
                }, 3000)
            }else{
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }

    }

    private fun onBoardingFinished(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}