package com.example.breakingnews.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.breakingnews.R
import com.example.breakingnews.utils.NewsUtils
import kotlinx.android.synthetic.main.activity_news_content_page.*

class NewsContentPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_content_page)

        val bundle: Bundle? = intent.extras

        val title = bundle!!.getString("title")
        val description = bundle.getString("description")
        val content = bundle.getString("content")
        val source = bundle.getString("source")
        val urlToImage = bundle.getString("urlToImage")
        val author = bundle.getString("author")
        val url = bundle.getString("url")

        Glide.with(this).load(urlToImage).placeholder(R.mipmap.w).into(img_content_image)
        text_content_source.text = source
        text_content_title.text = title
        text_content_description.text = description
        text_content_author.text = author
        text_content_content.text = content

        fab_content.setOnClickListener {
            val send: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, source)
                putExtra(Intent.EXTRA_TEXT, title)
                putExtra(Intent.EXTRA_TEXT, url)
                type = "text/plain"
            }

            val share = Intent.createChooser(send, null)
            startActivity(share)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0,0)
        finish()
    }
}
