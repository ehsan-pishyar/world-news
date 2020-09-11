package com.example.breakingnews.api

import com.example.breakingnews.model.NewsApi
import com.example.breakingnews.model.SourcesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): NewsApi

    @GET("/v2/everything")
    suspend fun getEverything(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String
    ): NewsApi

    @GET("/v2/sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String
    ): SourcesResponse

    @GET("/v2/everything")
    suspend fun getSourceContents(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String
    ): NewsApi
}