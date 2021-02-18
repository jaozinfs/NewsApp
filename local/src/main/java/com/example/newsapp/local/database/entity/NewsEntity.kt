package com.example.newsapp.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class NewsEntity(
    val title: String,
    val description: String,
    val content: String,
    val author: String,
    val publishedAt: Date,
    val highlight: Boolean,
    @PrimaryKey
    val url: String,
    val imageUrl: String
)