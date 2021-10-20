package com.example.listapp.app

import com.example.listapp.NetworkObserver
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@ActivityScoped
class MainController @Inject constructor(
	private val mainViewModel: MainViewModel,
	private val networkObserver: NetworkObserver
) {

	private var disposable: Disposable? = null

	fun subscribeNetworkStateChanges() {
		disposable?.dispose()
		disposable = networkObserver.observeNetworkStateChanges()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe { isConnected ->
				mainViewModel.setNetworkAvailable(isConnected)
			}
	}

	fun unsubscribe() {
		disposable?.dispose()
	}
}