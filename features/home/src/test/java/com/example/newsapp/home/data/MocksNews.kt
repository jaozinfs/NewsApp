package com.example.newsapp.home.data

import com.example.newsapp.home.domain.News
import java.util.*

object MocksNews {
    val mocks = listOf(
        News(
            "Teste 1",
            "Teste desc 1",
            "Teste content 1",
            "João Victor",
            Date(),
            false,
            "www.google.com",
            "www.facebook.com"
        ), News(
            "Teste 2",
            "Teste desc 2",
            "Teste content 2",
            "Pedro Victor",
            Date(),
            false,
            "www.rook.com",
            "www.teste.com"
        )
    )
}