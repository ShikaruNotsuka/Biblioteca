package com.example.biblioapp.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast

class NetworkChangeReceiver : BroadcastReceiver() {
    
    private var lastState: Boolean? = null

    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        val isConnected = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        if (lastState != null && lastState != isConnected) {
            if (isConnected) {
                Toast.makeText(context, "Conexión restaurada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Conexión a internet perdida", Toast.LENGTH_SHORT).show()
            }
        }
        lastState = isConnected
    }
}
