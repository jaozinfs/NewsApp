package com.example.newsapp.home.data.network

import com.google.gson.annotations.SerializedName

data class NewsEntity(
    val title: String,
    val description: String,
    val content: String,
    val author: String,
    @SerializedName("published_at")
    val publishedAt: String,
    val highlight: Boolean,
    val url: String,
    @SerializedName("image_url")
    val imageUrl: String
)
