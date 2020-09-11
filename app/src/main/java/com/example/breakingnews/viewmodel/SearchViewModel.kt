package com.example.breakingnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.breakingnews.model.NewsApi
import com.example.breakingnews.repository.SearchRepository

class SearchViewModel: ViewModel() {

    private var news: LiveData<NewsApi>? = null
    private val searchRepository: SearchRepository = SearchRepository()

    fun getNews(): LiveData<NewsApi>{
        return news!!
    }

    fun setQ(q: String){
        news = searchRepository.getNews(q)
    }
}