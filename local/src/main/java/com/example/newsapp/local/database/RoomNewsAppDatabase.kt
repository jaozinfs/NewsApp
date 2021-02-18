package com.example.newsapp.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.local.database.entity.NewsEntity
import com.example.newsapp.local.database.typeconverters.Converters

@Database(entities = [NewsEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class RoomNewsAppDatabase : RoomDatabase(), NewsAppDatabase {
}