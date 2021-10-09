package com.example.investmentguidevtb.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.investmentguidevtb.R
import com.example.investmentguidevtb.databinding.FragmentChatBinding
import com.example.investmentguidevtb.ui.profile.adapters.ChatAdapter
import com.example.investmentguidevtb.ui.profile.models.UserMessage

class ChatFragment() : Fragment(R.layout.fragment_chat) {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val view = binding.root

        val list = listOf(
            UserMessage(id=1, userId = 2, text = "Hello from VTB"),
            UserMessage(id=2, userId = 1, text = "Hi from User"),
            UserMessage(id=3, userId = 2, text = "What's up?"),
            UserMessage(id=4, userId = 1, text = "Everything is great!"),
            UserMessage(id=5, userId = 2, text = "Nice"),
        )

        val chatAdapter = ChatAdapter()
        binding.recyclerViewChat.adapter = chatAdapter
        chatAdapter.submitList(list)



        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}