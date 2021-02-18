package com.example.newsapp.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.example.newsapp.home.databinding.ItemNewsPagerBinding
import com.example.newsapp.home.domain.News

class NewsAdapter : PagingDataAdapter<News, NewsAdapter.NewsViewHolder>(NewsDiffUtils) {
    var favoriteClickListener: ((news: News) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(
            ItemNewsPagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindView(getItem(position) ?: return)
    }

    inner class NewsViewHolder(private val itemNewsBinding: ItemNewsPagerBinding) :
        RecyclerView.ViewHolder(itemNewsBinding.root) {

        fun bindView(news: News) = with(itemNewsBinding) {
            tvTitle.text = news.title
            btFavorite.setOnClickListener {
                favoriteClickListener?.invoke(news)
            }
            imvNewsImage.load(news.imageUrl) {
                memoryCachePolicy(CachePolicy.ENABLED)
            }
        }
    }

}