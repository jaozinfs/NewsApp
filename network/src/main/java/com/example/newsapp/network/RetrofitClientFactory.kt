package com.example.newsapp.network

interface RetrofitClientFactory {
    fun <T> createClient(api: Class<T>): T
    fun <T> createAuthenticatedClient(api: Class<T>): T
}
