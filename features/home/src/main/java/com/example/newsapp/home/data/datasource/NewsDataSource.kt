package com.example.newsapp.home.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.home.data.datasource.NewsDataSource.Page.INITIAL_PAGE
import com.example.newsapp.home.data.mapper.NewsMapper
import com.example.newsapp.home.data.network.NewsApi
import com.example.newsapp.home.domain.News
import com.example.newsapp.network.NetworkRequesterManager

class NewsDataSource(
    private val service: NewsApi,
    private val mapperImpl: NewsMapper
) : PagingSource<Int, News>() {
    private object Page {
        const val INITIAL_PAGE = 1
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            val newsList = getNews(page).data
            return LoadResult.Page(
                newsList,
                if (page == 1) null else page - 1,
                if (newsList.isEmpty()) null else page + 1
            )
        } catch (error: Exception) {
            LoadResult.Error(error)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition
    }

    private suspend fun getNews(page: Int) =
       NetworkRequesterManager.request { service.getNews(page) }.let {
            mapperImpl.mapPaginatedToDomain(it)
        }


}