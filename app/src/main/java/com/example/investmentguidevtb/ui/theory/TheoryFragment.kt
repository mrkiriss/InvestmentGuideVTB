package com.example.investmentguidevtb.ui.theory

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.investmentguidevtb.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TheoryFragment() : Fragment(R.layout.fragment_theory) {

    // FOR TESTING
    val viewModel by viewModels<TheoryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.data.observe(viewLifecycleOwner){

        }
    }

}