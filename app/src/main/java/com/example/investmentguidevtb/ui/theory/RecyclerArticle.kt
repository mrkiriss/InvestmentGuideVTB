package com.example.investmentguidevtb.ui.theory

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.investmentguidevtb.databinding.ItemTheoryBinding
import com.example.investmentguidevtb.ui.practice.models.GameArticle

class RecyclerArticle: RecyclerView.Adapter<RecyclerArticle.ArticleVH>() {

    var content: List<GameArticle> = emptyList()

    fun addContent(content: List<GameArticle>) {
        this.content = content
        notifyDataSetChanged()
    }

    class ArticleVH : RecyclerView.ViewHolder {

        private lateinit var binding: ItemTheoryBinding

        constructor(binding: ItemTheoryBinding) : super(binding.root) {
            this.binding = binding
        }

        fun bind(data: GameArticle) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleVH {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ArticleVH, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}