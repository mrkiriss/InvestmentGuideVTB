package com.example.investmentguidevtb.ui.theory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
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

        article?.also {
            binding.articleHeader.text = it.header
            binding.articleBody.text = it.body
            binding.articleAuthor.text = it.author
            Glide.with(this).load(it.image_url).into(binding.articleImage)
        }
    }

    companion object{
        @JvmStatic
        fun createBundle(articleContent: GameArticle?) = Bundle().apply {
            this.putSerializable("articleContent", articleContent)
        }
    }
}