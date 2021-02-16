package com.example.newsapp.home.data.network

import retrofit2.Response
import retrofit2.http.GET

interface NewsApi {

    @GET("/v1/client/news/highlights")
    suspend fun getHighlightsNews(): Response<List<NewsEntity>>

}