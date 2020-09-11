package com.example.breakingnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.breakingnews.model.NewsApi
import com.example.breakingnews.repository.SourceContentRepository

class SourceContentViewModel: ViewModel() {

    private var news: LiveData<NewsApi>? = null
    private val sourceContentRepository: SourceContentRepository = SourceContentRepository()

    fun getNews(): LiveData<NewsApi>{
        return news!!
    }

    fun setQ(q: String){
        news = sourceContentRepository.getSourceContent(q)
    }
}