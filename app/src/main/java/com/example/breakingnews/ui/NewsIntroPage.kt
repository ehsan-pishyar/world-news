package com.example.breakingnews.ui

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.breakingnews.R
import com.example.breakingnews.interfaces.InternetConnection
import com.example.breakingnews.utils.CheckNetwork
import kotlinx.android.synthetic.main.activity_news_intro_page.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsIntroPage : AppCompatActivity(), InternetConnection {

    private val checkNetwork: CheckNetwork = CheckNetwork(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_intro_page)
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(checkNetwork, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
    }

    override fun onStop() {
        unregisterReceiver(checkNetwork)
        super.onStop()
    }

    override fun connected() {
        CoroutineScope(Main).launch {
            delay(1000)
            progress_bar.visibility = View.VISIBLE
            delay(2000)
            text_connection.setText(R.string.connected)
            not_found_intro.visibility = View.VISIBLE
            startActivity(Intent(this@NewsIntroPage, MainActivity::class.java))
            finish()
        }
    }

    override fun notConnected() {
        CoroutineScope(Main).launch {
            delay(1000)
            progress_bar.visibility = View.VISIBLE
            delay(1000)
            text_connection.setText(R.string.no_internet_connection)
            not_found_intro.visibility = View.VISIBLE
        }
    }
}
