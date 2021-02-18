package com.example.newsapp.local.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.local.database.dao.NewsDao

interface NewsAppDatabase  {
    fun newsDao(): NewsDao
}