package com.example.newsapp.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.example.newsapp.home.databinding.ItemNewsBinding
import com.example.newsapp.home.domain.News

class NewsHighLightAdapter :
    ListAdapter<News, NewsHighLightAdapter.NewsViewHolder>(NewsDiffUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindView(getItem(position) ?: return)
    }


    inner class NewsViewHolder(private val itemNewsBinding: ItemNewsBinding) :
        RecyclerView.ViewHolder(itemNewsBinding.root) {

        fun bindView(news: News) = with(itemNewsBinding) {
            tvTitle.text = news.title
            imvNewsImage.load(news.imageUrl) {
                memoryCachePolicy(CachePolicy.ENABLED)
            }
        }
    }
}