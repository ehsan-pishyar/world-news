package com.example.breakingnews.interfaces

import com.example.breakingnews.model.Articles

interface SetOnNewsClickListener {

    fun onNewsClick(articles: Articles, position: Int)
}