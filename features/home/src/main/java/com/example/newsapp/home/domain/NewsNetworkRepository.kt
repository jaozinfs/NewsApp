package com.example.newsapp.home.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface NewsNetworkRepository  {
    suspend fun getHighlightsNews(): List<News>
    fun getNews(): Flow<PagingData<News>>
}