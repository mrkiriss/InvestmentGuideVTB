package com.example.investmentguidevtb.ui.practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.investmentguidevtb.R


class GameFeedbackFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_feedback, container, false)
    }

    companion object {
        fun createArguments(param1: String, param2: String) = Bundle().apply {

        }

    }
}