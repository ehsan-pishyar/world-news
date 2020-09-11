package com.example.breakingnews.interfaces

import com.example.breakingnews.model.Sources

interface SetOnSourcesClickListener {
    fun onSourcesTitleClick(sources: Sources, position: Int)
}