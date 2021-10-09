package com.example.investmentguidevtb.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.investmentguidevtb.R
import com.example.investmentguidevtb.databinding.FragmentChatBinding
import com.example.investmentguidevtb.ui.profile.adapters.ChatAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment() : Fragment(R.layout.fragment_chat) {

    private val viewModel by viewModels<ChatViewModel>()

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val view = binding.root

        val chatAdapter = ChatAdapter()
        binding.recyclerViewChat.adapter = chatAdapter

        viewModel.chatStateData.observe(viewLifecycleOwner){ listOfMessages ->
            chatAdapter.submitList(listOfMessages)
            chatAdapter.notifyDataSetChanged()
        }

        binding.apply {
            btnAnswer0.setOnClickListener { viewModel.answerQuestion(0) }
            btnAnswer1.setOnClickListener { viewModel.answerQuestion(1) }
            btnAnswer2.setOnClickListener { viewModel.answerQuestion(2) }
            btnAnswer3.setOnClickListener { viewModel.answerQuestion(3) }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}