package com.example.newsapp.local.database

import androidx.room.RoomDatabase

interface DatabaseFactory  {
    fun createDatabase(): NewsAppDatabase
}
