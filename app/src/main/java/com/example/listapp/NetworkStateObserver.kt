package com.example.listapp

import android.Manifest.permission
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class NetworkStateObserver @RequiresPermission(permission.ACCESS_NETWORK_STATE) @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val networkConnectionSubject = ConflatedBroadcastChannel<Boolean>()
    private val connectivityManager: ConnectivityManager?

    private val networkCallback: NetworkCallback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            updateNetworkSubject(true)
        }

        @RequiresPermission(permission.ACCESS_NETWORK_STATE)
        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            updateNetworkSubject(isConnectedToNetwork)
        }

        override fun onUnavailable() {
            super.onUnavailable()
            updateNetworkSubject(false)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            updateNetworkSubject(false)
        }
    }

    private fun updateNetworkSubject(isConnected: Boolean) {
        if (networkConnectionSubject.valueOrNull == null || networkConnectionSubject.value != isConnected) {
            networkConnectionSubject.offer(isConnected)
        }
    }

    @RequiresPermission(permission.ACCESS_NETWORK_STATE)
    private fun setupNetworkStateListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager!!.registerDefaultNetworkCallback(networkCallback)
        } else {
            connectivityManager!!.registerNetworkCallback(
                NetworkRequest.Builder().build(),
                networkCallback
            )
        }
    }

    /**
     * Check connectivity to network
     * @return true if connected, otherwise - false
     */
    @get:RequiresPermission(permission.ACCESS_NETWORK_STATE)
    val isConnectedToNetwork: Boolean
        get() {
            if (connectivityManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val network = connectivityManager.activeNetwork
                    if (network != null) {
                        val capabilities = connectivityManager.getNetworkCapabilities(network)
                        return capabilities != null && (capabilities.hasTransport(
                            NetworkCapabilities.TRANSPORT_CELLULAR
                        )
                                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                    }
                } else {
                    val networkInfo = connectivityManager.activeNetworkInfo
                    return networkInfo != null && networkInfo.isConnectedOrConnecting
                }
            }
            return false
        }

    @FlowPreview
    fun observeNetworkStateChanges(): Flow<Boolean> {
        return networkConnectionSubject.asFlow()
    }

    val isNetworkConnected: Boolean?
        get() = networkConnectionSubject.valueOrNull

    init {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        setupNetworkStateListener()
        networkConnectionSubject.offer(isConnectedToNetwork)
    }
}