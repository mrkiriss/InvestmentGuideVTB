package com.example.investmentguidevtb.ui.practice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.checkbox.checkBoxPrompt
import com.bumptech.glide.Glide
import com.example.investmentguidevtb.R
import com.example.investmentguidevtb.databinding.FragmentPracticeBinding
import com.example.investmentguidevtb.ui.theory.ArticleFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PracticeFragment() : Fragment(R.layout.fragment_practice) {

    private lateinit var binding: FragmentPracticeBinding
    private val viewModel by viewModels<PracticeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPracticeBinding.inflate(inflater)

        binding.motionLayout.setTransitionListener(object : TransitionAdapter() {
                override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                    when (currentId) {
                        R.id.rightOff -> {
                            viewModel.processSelectedSolution(0)
                            motionLayout.progress = 0f
                            motionLayout.setTransition(R.id.start, R.id.right)
                        }
                        R.id.bottomOff -> {
                            viewModel.processSelectedSolution(1)
                            motionLayout.progress = 0f
                            motionLayout.setTransition(R.id.start, R.id.bottom)
                        }
                        R.id.leftOff -> {
                            viewModel.processSelectedSolution(2)
                            motionLayout.progress = 0f
                            motionLayout.setTransition(R.id.start, R.id.left)
                        }
                    }
                }
            })

        initObservers()

        return binding.root
    }

    private fun initObservers() {
        viewModel.requestToUpdateSituation.observe(viewLifecycleOwner) {
            // change ui data
            binding.situationDescription.text = it.text
            Glide.with(this).load(it.image_url).into(binding.situationImage)
            binding.solutionRight.text = it.solutions[0].text
            binding.solutionBottom.text = it.solutions[1].text
            binding.solutionLeft.text = it.solutions[2].text
        }

        viewModel.requestToShowStepFeedback.observe(viewLifecycleOwner) {
            it.getContent()?.let {
                MaterialDialog(requireActivity()).show {

                    title(text = "Пояснение")

                    message(text = it.feedback)

                    checkBoxPrompt(text = "Не показывать в течение игры") { checked ->
                        // Check box was checked or unchecked
                    }

                    positiveButton(text = "Подробнее в статье") { dialog ->
                        findNavController(requireActivity(), R.id.nav_host_fragment_container)
                            .navigate(R.id.action_practiceFragment_to_articleFragment, ArticleFragment.createBundle(
                                it.article
                            ))
                    }

                    negativeButton(text = "Назад к игре") { dialog ->
                    }
                }
            }
        }

        viewModel.requestToShowToastContent.observe(viewLifecycleOwner) {
            Toast.makeText(context, it?: "", LENGTH_SHORT).show()
        }

        viewModel.requestToChangeCardVisible.observe(viewLifecycleOwner) {
            binding.card.visibility = if (it == true) View.VISIBLE else View.GONE
        }
    }

}