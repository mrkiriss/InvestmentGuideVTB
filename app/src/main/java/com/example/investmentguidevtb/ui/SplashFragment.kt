package com.example.investmentguidevtb.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.investmentguidevtb.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment(): Fragment(R.layout.fragment_splash) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        requireActivity().findViewById<AppBarLayout>(R.id.appBarLayout).visibility = View.GONE
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view).visibility = View.GONE

        Handler().postDelayed({
            if(onBoardingFinished()){
                val action = SplashFragmentDirections.actionSplashFragmentToProfileFragment()
                findNavController().navigate(action)
            }else{
                val action = SplashFragmentDirections.actionSplashFragmentToViewPagerFragment()
                findNavController().navigate(action)
            }
        }, 3000)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun onBoardingFinished(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}