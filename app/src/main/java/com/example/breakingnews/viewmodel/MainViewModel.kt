package com.example.breakingnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.breakingnews.model.NewsApi
import com.example.breakingnews.repository.MainRepository

class MainViewModel : ViewModel() {

    private var news: LiveData<NewsApi>?


    init {
        val mainRepository = MainRepository()
        news = mainRepository.getNews()
    }

    fun getNews(): LiveData<NewsApi>{
        return news!!
    }

}