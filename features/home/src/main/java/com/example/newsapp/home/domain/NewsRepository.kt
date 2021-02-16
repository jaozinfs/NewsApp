package com.example.newsapp.home.domain

interface NewsRepository  {

    suspend fun getHighlightsNews(): List<News>
    suspend fun getNews(): List<News>

}