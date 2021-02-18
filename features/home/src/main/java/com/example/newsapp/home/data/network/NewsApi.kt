package com.example.newsapp.home.data.network

import com.example.newsapp.network.base.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {
    @GET("/v1/client/news/highlights")
    suspend fun getHighlightsNews(): Response<BaseResponse<List<NewsEntity>>>

    @GET("/v1/client/news")
    suspend fun getNews(
        @Query("current_page") currentPage: Int
    ): Response<BaseResponse<List<NewsEntity>>>

}