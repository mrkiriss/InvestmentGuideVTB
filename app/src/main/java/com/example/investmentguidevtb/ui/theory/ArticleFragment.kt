package com.example.investmentguidevtb.ui.theory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.investmentguidevtb.R
import com.example.investmentguidevtb.databinding.FragmentArticleBinding
import com.example.investmentguidevtb.ui.practice.models.GameArticle

class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentArticleBinding.inflate(layoutInflater)

        checkArguments()

        return binding.root
    }

    private fun checkArguments() {
        val article: GameArticle? = arguments?.getSerializable("articleContent")?.let {
            it as GameArticle
        }

        article?.apply {
            //binding.articleImage
            binding.articleHeader.text = header
            binding.articleBody.text = body
            binding.articleAuthor.text = author
        }
    }

    companion object{
        @JvmStatic
        fun createBundle(articleContent: GameArticle?) = Bundle().apply {
            this.putSerializable("articleContent", articleContent)
        }
    }
}