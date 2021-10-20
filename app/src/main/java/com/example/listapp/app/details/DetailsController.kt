package com.example.listapp.app.details

import com.example.listapp.NetworkObserver
import com.example.listapp.logic.DataRepository
import dagger.hilt.android.scopes.FragmentScoped
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@FragmentScoped
class DetailsController @Inject constructor(
	private val viewModel: DetailsViewModel,
	private val networkObserver: NetworkObserver,
	private val dataRepository: DataRepository
) {

	private var networkStateDisposable: Disposable? = null
	private var loadDataDisposable: Disposable? = null

	fun loadPost(id: Int) {
		viewModel.setTitle("")
		viewModel.setDetails("")
		if (viewModel.isNetworkAvailable()) {
			viewModel.setRefreshing(true)
			loadDataDisposable?.dispose()
			loadDataDisposable = dataRepository.getPost(id)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
					{ post ->
						viewModel.setTitle(post.title)
						viewModel.setDetails(post.body)
						viewModel.setRefreshing(false)
						viewModel.setPlaceholderVisible(false)
					}, { t ->
						Timber.e(t)
						viewModel.showError(t)
						viewModel.setRefreshing(false)
						viewModel.setPlaceholderVisible(true)
					}
				)
		}
	}

	fun onBackClick() {
		viewModel.navUp()
	}

	fun subscribeNetworkStateChanges() {
		networkStateDisposable?.dispose()
		networkStateDisposable = networkObserver.observeNetworkStateChanges()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe { isConnected ->
				viewModel.setNetworkAvailable(isConnected)
				viewModel.setPullToRefreshEnabled(isConnected)
			}
	}

	fun unsubscribe() {
		networkStateDisposable?.dispose()
	}

	fun clear() {
		loadDataDisposable?.dispose()
	}
}