package com.example.newsapp.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.example.newsapp.home.databinding.ItemNewsPagerBinding
import com.example.newsapp.home.domain.News

class FavoritesNewsAdapter : ListAdapter<News, CommonNewsViewHolder>(NewsDiffUtils) {
    var favoriteClickListener: ((news: News) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonNewsViewHolder =
        CommonNewsViewHolder(
            ItemNewsPagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: CommonNewsViewHolder, position: Int) {
        holder.bindView(getItem(position) ?: return, favoriteClickListener)
    }


}