package com.example.newsapp.home.domain

import com.google.gson.annotations.SerializedName

data class News(
    val title: String,
    val description: String,
    val content: String,
    val author: String,
    val publishedAt: String,
    val highlight: Boolean,
    val url: String,
    val imageUrl: String
)