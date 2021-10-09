package com.example.investmentguidevtb.ui.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.fragment.app.Fragment
import com.example.investmentguidevtb.R
import com.example.investmentguidevtb.databinding.FragmentPracticeBinding

class PracticeFragment() : Fragment(R.layout.fragment_practice) {

    private lateinit var binding: FragmentPracticeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPracticeBinding.inflate(inflater)


        binding.motionLayout.setTransitionListener(object : TransitionAdapter() {
                override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                    when (currentId) {
                        R.id.rightOff,
                        R.id.leftOff,
                        R.id.bottomOff -> {
                            motionLayout.progress = 0f
                            motionLayout.setTransition(R.id.start, R.id.right)
                        }
                    }
                }
            })

        return binding.root
    }

}