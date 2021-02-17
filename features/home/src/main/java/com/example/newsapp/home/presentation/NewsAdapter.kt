package com.example.newsapp.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.home.databinding.ItemNewsBinding
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.presentation.viewmodel.NewsDiffUtils

class NewsAdapter : PagingDataAdapter<News, NewsAdapter.NewsViewHolder>(NewsDiffUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false).let {
            NewsViewHolder(it)
        }


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindView(getItem(position) ?: return)
    }


    inner class NewsViewHolder(private val itemNewsBinding: ItemNewsBinding) :
        RecyclerView.ViewHolder(itemNewsBinding.root) {

        fun bindView(news: News) = with(itemNewsBinding) {
            tvTitle.text = news.title
        }
    }
}