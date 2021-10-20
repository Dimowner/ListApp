package com.example.listapp

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.content.Context
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.os.Build
import androidx.annotation.RequiresPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkObserver @Inject constructor(
	@ApplicationContext private val context: Context
) {

	private val networkConnectionSubject = BehaviorSubject.create<Boolean>()
	private var connectivityManager: ConnectivityManager? =
		context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

	private val networkCallback: NetworkCallback = object : NetworkCallback() {
		override fun onAvailable(network: Network) {
			super.onAvailable(network)
			updateNetworkSubject(true)
		}

		override fun onLosing(network: Network, maxMsToLive: Int) {
			super.onLosing(network, maxMsToLive)
			updateNetworkSubject(isConnectedToNetwork())
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

	init {
		setupNetworkStateListener()
		updateNetworkSubject(isConnectedToNetwork())
	}

	private fun updateNetworkSubject(isConnected: Boolean) {
		val value = networkConnectionSubject.value
		if (value == null || value != isConnected) {
			networkConnectionSubject.onNext(isConnected)
		}
	}

	fun observeNetworkStateChanges(): Observable<Boolean> {
		return networkConnectionSubject
	}

	private fun setupNetworkStateListener() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			connectivityManager?.registerDefaultNetworkCallback(networkCallback)
		} else {
			connectivityManager?.registerNetworkCallback(
				NetworkRequest.Builder().build(),
				networkCallback
			)
		}
	}

	/**
	 * Check connectivity to network
	 * @return true if connected, otherwise - false
	 */
	@RequiresPermission(ACCESS_NETWORK_STATE)
	private fun isConnectedToNetwork(): Boolean {
		connectivityManager?.let { manager ->
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				val network = manager.activeNetwork
				if (network != null) {
					val capabilities = manager.getNetworkCapabilities(network)
					return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
							|| capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
				}
			} else {
				val networkInfo = manager.activeNetworkInfo
				return networkInfo != null && networkInfo.isConnectedOrConnecting
			}
		}
		return false
	}
}
