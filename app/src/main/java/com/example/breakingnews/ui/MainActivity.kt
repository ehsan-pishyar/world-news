package com.example.breakingnews.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.breakingnews.R
import com.example.breakingnews.adapters.NewsListAdapter
import com.example.breakingnews.model.Articles
import com.example.breakingnews.interfaces.SetOnNewsClickListener
import com.example.breakingnews.utils.NewsUtils
import com.example.breakingnews.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_main_row_card.*
import kotlinx.android.synthetic.main.toolbar.*
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity(), SetOnNewsClickListener {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var newsListAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        overridePendingTransition(0, 0)

        initViewModel()
        initUi()
        initBottomBar()
    }

    private fun initViewModel(){
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun initUi(){
        mainViewModel.getNews().observe(this, Observer {news ->
            recyclerBody(news.articles)
        })
    }

    private fun recyclerBody(articles: List<Articles>){
        main_recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            newsListAdapter = NewsListAdapter(articles, this@MainActivity)
            setHasFixedSize(true)
            val recyclerDecoration = DividerItemDecoration(this@MainActivity, VERTICAL)
            addItemDecoration(recyclerDecoration)
            adapter = newsListAdapter
        }
    }

    private fun initBottomBar(){
        bottom_bar.setOnTabSelectListener(object: AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when(newTab.id){
                    R.id.favorite_bottom_nav -> {
                        startActivity(Intent(this@MainActivity, SourcesPage::class.java))
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    R.id.search_bottom_nav -> {
                        startActivity(Intent(this@MainActivity, SearchNewsPage::class.java))
                        overridePendingTransition(0, 0)
                        finish()
                    }
                }
            }
        })
    }

    override fun onNewsClick(articles: Articles, position: Int) {
        val intent = Intent(this, NewsContentPage::class.java)
        intent.putExtra("title", articles.title)
        intent.putExtra("description", articles.description)
        intent.putExtra("content", articles.content)
        intent.putExtra("author", articles.author)
        intent.putExtra("urlToImage", articles.urlToImage)
        intent.putExtra("source", articles.source.name)
        intent.putExtra("url", articles.url)

        startActivity(intent)
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
                overridePendingTransition(0, 0)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
