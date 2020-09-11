package com.example.breakingnews.repository

import androidx.lifecycle.LiveData
import com.example.breakingnews.api.NewsRetrofitBuilder
import com.example.breakingnews.model.NewsApi
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class SearchRepository {

    private val job: CompletableJob? = null

    fun getNews(q: String): LiveData<NewsApi>{
        val job = Job()
        return object : LiveData<NewsApi>(){
            override fun onActive() {
                super.onActive()
                job.let {theJob ->
                    CoroutineScope(IO + theJob).launch {
                        val news = NewsRetrofitBuilder.apiService.getEverything(q, NewsRetrofitBuilder.API_KEY)
                        withContext(Main){
                            value = news
                            job.complete()
                        }
                    }
                }
            }
        }
    }

    fun cancelJobs(){
        job!!.cancel()
    }
}