package com.example.newsapp.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.local.database.entity.NewsEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM newsentity")
    fun getNews(): LiveData<List<NewsEntity>>

    @Insert
    suspend fun addNews(newsEntity: NewsEntity)

    @Delete
    suspend fun removeNews(newsEntity: NewsEntity)

    @Query("SELECT * FROM newsentity WHERE url LIKE :urlMatch")
    suspend fun getNewsByUrl(urlMatch: String): NewsEntity?
}