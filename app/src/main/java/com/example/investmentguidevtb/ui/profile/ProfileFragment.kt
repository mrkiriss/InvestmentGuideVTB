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
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
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

        requireActivity().findViewById<AppBarLayout>(R.id.appBarLayout).visibility = View.VISIBLE
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view).visibility = View.VISIBLE

        binding.btnStartSegmentation.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToChatFragment()
            findNavController().navigate(action)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.event.collect { event ->
                when(event){
                    is ProfileViewModel.Event.SegmentationPassed ->  {
                        binding.apply {
                            infoMessage.text = "Вы прошли анкетирование. Можете приступать к игре!"
                            btnStartSegmentation.visibility = View.GONE
                        }
                    }
                    is ProfileViewModel.Event.SegmentationNotPassed -> {
                        binding.apply {
                            infoMessage.text = "У нас пока нет о Вас данных"
                            btnStartSegmentation.visibility = View.VISIBLE
                        }
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