package com.example.breakingnews.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingnews.R
import com.example.breakingnews.adapters.NewsListAdapter
import com.example.breakingnews.model.*
import com.example.breakingnews.interfaces.SetOnNewsClickListener
import com.example.breakingnews.viewmodel.SourceContentViewModel
import kotlinx.android.synthetic.main.activity_source_content_page.*

class SourceContentPage : AppCompatActivity(), SetOnNewsClickListener {

    private lateinit var sourceContentViewModel: SourceContentViewModel
    private lateinit var newsListAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_source_content_page)

        val sourceId = getSourceId()
        initViewModel()
        setQ(sourceId)
        initUi()
    }

    private fun initViewModel(){
        sourceContentViewModel = ViewModelProvider(this).get(SourceContentViewModel::class.java)
    }

    private fun initUi(){
        sourceContentViewModel.getNews().observe(this, Observer { news ->
            recyclerBody(news.articles)
        })
    }

    private fun setQ(q: String){
        sourceContentViewModel.setQ(q)
    }

    private fun getSourceId(): String{
        val bundle: Bundle? = intent.extras
        val sourceId = bundle!!.getString("id")
        return sourceId!!
    }

    private fun recyclerBody(articles: List<Articles>){
        recycler_source_content.apply {
            layoutManager = LinearLayoutManager(this@SourceContentPage)
            newsListAdapter = NewsListAdapter(articles, this@SourceContentPage)
            setHasFixedSize(true)
            val recyclerDecoration = DividerItemDecoration(this@SourceContentPage, RecyclerView.VERTICAL)
            addItemDecoration(recyclerDecoration)
            adapter = newsListAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.about_menu -> {
                startActivity(Intent(this, AboutMePage::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(0, 0)
        finish()
    }

    override fun onNewsClick(articles: Articles, position: Int) {
        val intent = Intent(this, NewsContentPage::class.java)
        intent.putExtra("title", articles.title)
        intent.putExtra("description", articles.description)
        intent.putExtra("content", articles.content)
        intent.putExtra("author", articles.author)
        intent.putExtra("urlToImage", articles.urlToImage)
        intent.putExtra("source", articles.source.name)

        startActivity(intent)
    }
}
