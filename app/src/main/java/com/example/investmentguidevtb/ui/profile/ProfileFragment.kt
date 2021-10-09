package com.example.investmentguidevtb.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.investmentguidevtb.R
import com.example.investmentguidevtb.databinding.FragmentProfileBinding
import com.example.investmentguidevtb.ui.profile.adapters.ChatAdapter
import com.example.investmentguidevtb.ui.profile.models.UserMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ProfileFragment() : Fragment(R.layout.fragment_profile) {

    private val viewModel by viewModels<ProfileViewModel>()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnStartSegmentation.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToChatFragment()
            findNavController().navigate(action)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.event.collect { event ->
                when(event){
                    is ProfileViewModel.Event.SegmentationPassed ->  {

                    }
                    is ProfileViewModel.Event.SegmentationNotPassed -> {

                    }
                }
            }
        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}