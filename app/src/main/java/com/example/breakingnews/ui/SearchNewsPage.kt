package com.example.breakingnews.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingnews.R
import com.example.breakingnews.adapters.NewsListAdapter
import com.example.breakingnews.model.Articles
import com.example.breakingnews.interfaces.SetOnNewsClickListener
import com.example.breakingnews.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.bottom_bar
import kotlinx.android.synthetic.main.activity_search_news_page.*
import kotlinx.android.synthetic.main.toolbar.*
import nl.joery.animatedbottombar.AnimatedBottomBar

class SearchNewsPage : AppCompatActivity(), SetOnNewsClickListener {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var newsListAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_news_page)
        setSupportActionBar(toolbar)
        overridePendingTransition(0, 0)

        initViewModel()
        initBottomBar()
        initSearchField()
    }

    private fun initViewModel(){
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    private fun setQ(q: String){
        searchViewModel.setQ(q)
    }

    private fun initUi(){
        searchViewModel.getNews().observe(this, Observer { news ->
            recyclerBody(news.articles)
        })
    }

    private fun initSearchField() {
        etxt_search.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                checkSearchField(s.toString().trim())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                recycler_search.visibility = View.INVISIBLE
                not_found_search.visibility = View.VISIBLE
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkSearchField(s.toString().trim())
            }
        })
    }

    private fun checkSearchField(char: String){
        if (char.isEmpty()){
            recycler_search.visibility = View.INVISIBLE
            not_found_search.visibility = View.VISIBLE
        }else{
            recycler_search.visibility = View.VISIBLE
            not_found_search.visibility = View.INVISIBLE
            setQ(char)
            initUi()
        }
    }

    /*
    private fun initRecyclerview(text: String){
        val service = NewsFactory.getRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getEverything(text, NewsFactory.API_KEY)
            withContext(Dispatchers.Main) {
                try {
                    val allNews: NewsApi? = response.body()
                    recyclerBody(allNews!!.articles)
                } catch (e: HttpException) {
                    e.message()
                } catch (e: Throwable) {
                    e.message
                }
            }
        }
    }
     */

    private fun recyclerBody(articles: List<Articles>){
        recycler_search.apply {
            layoutManager = LinearLayoutManager(this@SearchNewsPage)
            newsListAdapter = NewsListAdapter(articles, this@SearchNewsPage)
            setHasFixedSize(true)
            val recyclerDecoration = DividerItemDecoration(this@SearchNewsPage, RecyclerView.VERTICAL)
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
                    R.id.home_bottom_nav -> {
                        startActivity(Intent(this@SearchNewsPage, MainActivity::class.java))
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    R.id.favorite_bottom_nav -> {
                        startActivity(Intent(this@SearchNewsPage, SourcesPage::class.java))
                        overridePendingTransition(0, 0)
                        finish()
                    }
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // make this shit a separate function
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
