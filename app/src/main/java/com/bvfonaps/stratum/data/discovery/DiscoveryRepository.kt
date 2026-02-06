package com.bvfonaps.stratum.data.discovery

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.DatagramSocket
import java.net.SocketTimeoutException

class DiscoveryRepository(
    private val context: Context
) {
    @SuppressLint("ServiceCast")
    suspend fun discoverServer(): String? {
//        val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
//        val lock = wifi.createMulticastLock("udp_discovery")
//        lock.acquire()

        return try {
            val response = discoverServerUdp()
            response?.let { parseServerResponse(it)}
        } finally {

        }
    }

    private suspend fun discoverServerUdp(timeoutMs: Int = 2000): String? = withContext(Dispatchers.IO) {
//        val socket = DatagramSocket().apply {
//            broadcast = true
//            soTimeout = timeoutMs
//        }

        try {
            "SERVER_IP:192.168.1.42:8999"
        } catch (e: SocketTimeoutException) {
            null
        } finally {
            null
        }
    }

    private fun parseServerResponse(response: String): String {
        val parts = response.split(":")
        val ip = parts[1]
        val port = parts[2]
        return "http://$ip:$port/"
    }
}