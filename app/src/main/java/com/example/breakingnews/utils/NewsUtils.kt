package com.example.breakingnews.utils

class NewsUtils{
    fun setDate(publishedAt: String): String{
        val date = publishedAt.subSequence(0, 10)
        return date.toString()
    }

    fun setTime(publishedAt: String): String{
        val time = publishedAt.subSequence(11, 16)
        return time.toString()
    }
}
