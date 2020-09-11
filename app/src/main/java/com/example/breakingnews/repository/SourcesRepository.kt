package com.example.breakingnews.repository

import androidx.lifecycle.LiveData
import com.example.breakingnews.api.NewsRetrofitBuilder
import com.example.breakingnews.model.SourcesResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class SourcesRepository {

    private val job: CompletableJob? = null

    fun getSources(): LiveData<SourcesResponse>{
        val job = Job()
        return object : LiveData<SourcesResponse>(){
            override fun onActive() {
                super.onActive()
                job.let { theJob ->
                    CoroutineScope(IO + theJob).launch {
                        val sources = NewsRetrofitBuilder.apiService.getSources(NewsRetrofitBuilder.API_KEY)
                        withContext(Main){
                            value = sources
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