package com.example.listapp.app.posts

import com.example.listapp.NetworkObserver
import com.example.listapp.logic.DataRepository
import dagger.hilt.android.scopes.FragmentScoped
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@FragmentScoped
class PostsListController @Inject constructor(
	private val viewModel: PostsListViewModel,
	private val networkObserver: NetworkObserver,
	private val dataRepository: DataRepository
) {

	private var networkStateDisposable: Disposable? = null
	private var loadDataDisposable: Disposable? = null

	fun loadPosts() {
		if (viewModel.isNetworkAvailable()) {
			viewModel.setRefreshing(true)
			loadDataDisposable?.dispose()
			loadDataDisposable = dataRepository.getPosts()
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(
						{ list ->
							viewModel.setList(list.map { PostListItem(it.id, it.title, it.body) })
							viewModel.setRefreshing(false)
							viewModel.setPlaceholderVisible(list.isEmpty())
						}, { t ->
							Timber.e(t)
							viewModel.showError(t)
							viewModel.setRefreshing(false)
							viewModel.setPlaceholderVisible(true)
						}
					)
		}
	}

	fun onItemClick(id: Int) {
		viewModel.selectItem(id)
	}

	fun subscribeNetworkStateChanges() {
		networkStateDisposable?.dispose()
		networkStateDisposable = networkObserver.observeNetworkStateChanges()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe { isConnected ->
				viewModel.setPullToRefreshEnabled(isConnected)
				viewModel.setNetworkAvailable(isConnected)
			}
	}

	fun unsubscribe() {
		networkStateDisposable?.dispose()
	}

	fun clear() {
		loadDataDisposable?.dispose()
	}
}