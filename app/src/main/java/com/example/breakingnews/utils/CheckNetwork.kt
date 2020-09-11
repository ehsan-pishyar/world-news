package com.example.breakingnews.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import com.example.breakingnews.interfaces.InternetConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckNetwork(private val internetConnection: InternetConnection) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(IO).launch {
            val connectivityManager: ConnectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            val isConnected: Boolean = networkInfo != null && networkInfo.isConnectedOrConnecting
            if (isConnected){
                delay(2000)
                internetConnection.connected()
            }else{
                internetConnection.notConnected()
            }
        }
    }
}