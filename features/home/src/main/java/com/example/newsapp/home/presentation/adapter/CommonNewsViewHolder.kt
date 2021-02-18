package com.example.newsapp.home.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.example.newsapp.home.databinding.ItemNewsPagerBinding
import com.example.newsapp.home.domain.News

class CommonNewsViewHolder(
    private val itemNewsBinding: ItemNewsPagerBinding
) :
    RecyclerView.ViewHolder(itemNewsBinding.root) {

    fun bindView(
        news: News,
       favoriteClickListener: ((News) -> Unit)?
    ) = with(itemNewsBinding) {
        tvTitle.text = news.title
        btFavorite.setOnClickListener {
            favoriteClickListener?.invoke(news)
        }
        tvDesc.text = news.description
        imvNewsImage.load(news.imageUrl) {
            memoryCachePolicy(CachePolicy.ENABLED)
        }
    }
}