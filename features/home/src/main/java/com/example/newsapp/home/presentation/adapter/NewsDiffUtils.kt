package com.example.newsapp.home.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.newsapp.home.domain.News

object NewsDiffUtils : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean = oldItem.url == newItem.url


    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean = newItem == newItem


}