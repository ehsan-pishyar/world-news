package com.example.breakingnews.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsRetrofitBuilder {

    private const val API_URL = "http://newsapi.org"
    private const val API_URL_S = "https://newsapi.org"
    const val API_KEY = "240f47a72fb24b629971cdc81785331a"

    private val retrofitBuilder: Retrofit.Builder by lazy {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
            Retrofit.Builder()
                .baseUrl(API_URL_S)
                .addConverterFactory(GsonConverterFactory.create())
        } else{
            Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
        }
    }

    val apiService: NewsApiService by lazy {
        retrofitBuilder.build().create(NewsApiService::class.java)
    }
}