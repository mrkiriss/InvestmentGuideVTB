package com.example.investmentguidevtb.ui.practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.investmentguidevtb.R
import com.example.investmentguidevtb.databinding.FragmentStartGameBinding

class StartGameFragment : Fragment() {

    private lateinit var binding: FragmentStartGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStartGameBinding.inflate(inflater)

        binding.button.setOnClickListener(){
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container).navigate(R.id.action_startGameFragment_to_practiceFragment);
        }

        return binding.root
    }
}