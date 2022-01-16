package com.example.listapp.app

import com.example.listapp.NetworkStateObserver
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ActivityScoped
class MainController @Inject constructor(
	private val mainViewModel: MainViewModel,
	private val networkObserver: NetworkStateObserver
) {

	@FlowPreview
	suspend fun subscribeNetworkStateChanges() {
		networkObserver.observeNetworkStateChanges()
			.catch { Timber.e(it) }
			.collect { mainViewModel.setNetworkAvailable(it) }
	}
}