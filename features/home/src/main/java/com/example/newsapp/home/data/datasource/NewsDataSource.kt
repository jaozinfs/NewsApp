package com.example.newsapp.home.data.datasource

import androidx.paging.*
import com.example.newsapp.home.data.datasource.NewsDataSource.Page.INITIAL_PAGE
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.domain.NewsRepository

class NewsDataSource(
    private val service: NewsRepository
) : PagingSource<Int, News>() {
    private object Page {
        const val INITIAL_PAGE = 1
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            val newsList = service.getHighlightsNews()
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


}