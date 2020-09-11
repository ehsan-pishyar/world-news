package com.example.breakingnews.model

data class NewsApi(
    val status: String,
    val totalResult: Int,
    val articles: List<Articles>
)