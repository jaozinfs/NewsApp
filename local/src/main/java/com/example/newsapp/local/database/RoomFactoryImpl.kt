package com.example.newsapp.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object RoomFactoryImpl : DatabaseFactory, KoinComponent {

    private val context: Context by inject()
    private const val DATABASE_NAME = "newsAppDatabase"

    override fun createDatabase(): NewsAppDatabase =
        Room.databaseBuilder(
            context,
            RoomNewsAppDatabase::class.java,
            DATABASE_NAME
        ).build()
}
