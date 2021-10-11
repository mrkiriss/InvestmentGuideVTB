package com.example.investmentguidevtb.ui.theory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.investmentguidevtb.R
import com.example.investmentguidevtb.databinding.ItemTheoryBinding
import com.example.investmentguidevtb.ui.practice.models.GameArticle

class RecyclerArticle(val callback: (GameArticle) -> Unit) :
    RecyclerView.Adapter<RecyclerArticle.ArticleVH>() {

    var content: List<GameArticle> = emptyList()
     set(value) {
         field = value
         notifyDataSetChanged()
     }

    class ArticleVH(private val binding: ItemTheoryBinding, val callback: (GameArticle) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        private var data: GameArticle? = null

        init {
            binding.container.setOnClickListener() {
                data?.also {
                    callback.invoke(it)
                }
            }
        }

        fun bind(data: GameArticle) {
            this.data = data
            binding.articleTitle.text = data.header
            binding.articleDescription.text = data.body
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleVH {
        return ArticleVH(ItemTheoryBinding.inflate(LayoutInflater.from(parent.context), parent, false), callback)
    }

    override fun onBindViewHolder(holder: ArticleVH, position: Int) {
        holder.bind(content[position])
    }

    override fun getItemCount() =
        content.size
}