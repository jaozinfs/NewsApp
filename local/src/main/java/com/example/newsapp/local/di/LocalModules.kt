package com.example.newsapp.local.di

import androidx.room.RoomDatabase
import com.example.newsapp.local.database.DatabaseFactory
import com.example.newsapp.local.database.NewsAppDatabase
import com.example.newsapp.local.database.RoomFactoryImpl
import org.koin.dsl.module

val localModules = module {
    single<DatabaseFactory> { RoomFactoryImpl }
    single { get<DatabaseFactory>().createDatabase() }
    single { get<NewsAppDatabase>().newsDao() }
}