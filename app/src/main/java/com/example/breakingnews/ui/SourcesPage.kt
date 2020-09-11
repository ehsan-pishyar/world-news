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
import com.example.breakingnews.adapters.SourcesListAdapter
import com.example.breakingnews.model.Sources
import com.example.breakingnews.interfaces.SetOnSourcesClickListener
import com.example.breakingnews.viewmodel.SourcesViewModel
import kotlinx.android.synthetic.main.activity_main.bottom_bar
import kotlinx.android.synthetic.main.activity_sources_page.*
import kotlinx.android.synthetic.main.toolbar.*
import nl.joery.animatedbottombar.AnimatedBottomBar

class SourcesPage : AppCompatActivity(), SetOnSourcesClickListener {

    private lateinit var sourcesViewModel: SourcesViewModel
    private lateinit var sourcesListAdapter: SourcesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sources_page)
        setSupportActionBar(toolbar)
        overridePendingTransition(0, 0)

        initViewModel()
        initUi()
        initBottomBar()
    }

    private fun initViewModel(){
        sourcesViewModel = ViewModelProvider(this).get(SourcesViewModel::class.java)
    }

    private fun initUi(){
        sourcesViewModel.getSources().observe(this, Observer { sources ->
            recyclerBody(sources.sources)
        })
    }

    private fun recyclerBody(sources: List<Sources>){
        recycler_sources.apply {
            layoutManager = LinearLayoutManager(this@SourcesPage)
            sourcesListAdapter = SourcesListAdapter(sources, this@SourcesPage)
            setHasFixedSize(true)
            val recyclerDecoration = DividerItemDecoration(this@SourcesPage, RecyclerView.VERTICAL)
            addItemDecoration(recyclerDecoration)
            adapter = sourcesListAdapter
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
                        startActivity(Intent(this@SourcesPage, MainActivity::class.java))
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    R.id.search_bottom_nav -> {
                        startActivity(Intent(this@SourcesPage, SearchNewsPage::class.java))
                        overridePendingTransition(0,0)
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

    override fun onSourcesTitleClick(sources: Sources, position: Int) {
        val intent = Intent(this, SourceContentPage::class.java)
        intent.putExtra("id", sources.id)

        startActivity(intent)
    }
}
