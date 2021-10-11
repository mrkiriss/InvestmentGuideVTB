package com.example.investmentguidevtb.ui.theory

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.investmentguidevtb.R
import com.example.investmentguidevtb.databinding.FragmentTheoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TheoryFragment : Fragment(R.layout.fragment_theory) {

    val viewModel by viewModels<TheoryViewModel>()

    private lateinit var binding: FragmentTheoryBinding
    private val adapter = RecyclerArticle() {
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)
            .navigate(R.id.action_theoryFragment_to_articleFragment, ArticleFragment.createBundle(it))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTheoryBinding.bind(view)

        binding.recuclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recuclerView.adapter = adapter

        viewModel.requestToast.observe(viewLifecycleOwner){
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.requestAddList.observe(viewLifecycleOwner) {
            adapter.content = it
        }
    }
}