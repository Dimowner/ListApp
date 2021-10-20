package com.example.listapp.app.details

import com.example.listapp.NetworkObserver
import dagger.hilt.android.scopes.FragmentScoped
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

@FragmentScoped
class DetailsController @Inject constructor(
	private val detailsViewModel: DetailsViewModel,
	private val networkObserver: NetworkObserver
) {

	private var disposable: Disposable? = null

	fun init(id: String) {
		Timber.v(id)
		detailsViewModel.setTitle(id)
	}

	fun subscribeNetworkStateChanges() {
		disposable?.dispose()
		disposable = networkObserver.observeNetworkStateChanges()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe { isConnected ->

			}
	}

	fun unsubscribe() {
		disposable?.dispose()
	}
}