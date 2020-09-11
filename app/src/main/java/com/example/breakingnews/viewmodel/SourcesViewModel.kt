package com.example.breakingnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.breakingnews.model.SourcesResponse
import com.example.breakingnews.repository.SourcesRepository

class SourcesViewModel: ViewModel() {

    private var sources: LiveData<SourcesResponse>?

    init {
        val sourcesRepository = SourcesRepository()
        sources = sourcesRepository.getSources()
    }

    fun getSources(): LiveData<SourcesResponse>{
        return sources!!
    }
}