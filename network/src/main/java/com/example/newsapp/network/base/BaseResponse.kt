package com.example.newsapp.network.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(val pagination: Pagination? = null, val data: T)
data class Pagination(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total_pages")
    val totalPage: Int,
    @SerializedName("total_items")
    val totalItems: Int
)