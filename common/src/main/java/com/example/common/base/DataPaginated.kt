package com.example.common.base


data class DataPaginated<T>(val pagination: Pagination?, val data: List<T>)
data class Pagination(
    val currentPage: Int?,
    val totalPage: Int?
)