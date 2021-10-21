package com.example.listapp.app.posts

import com.example.listapp.NetworkObserver
import com.example.listapp.logic.DataRepository
import com.example.listapp.logic.LocalDataRepository
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
	private val dataRepository: DataRepository,
	private val localDataRepository: LocalDataRepository
) {

	private var networkStateDisposable: Disposable? = null
	private var loadDataDisposable: Disposable? = null
	private var observeDataDisposable: Disposable? = null

	fun loadPosts() {
		if (viewModel.isNetworkAvailable()) {
			viewModel.setRefreshing(true)
			loadDataDisposable?.dispose()
			loadDataDisposable = dataRepository.getPosts()
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(
						{ list ->
							localDataRepository.insertPosts(list)
								.subscribeOn(Schedulers.io())
								.observeOn(AndroidSchedulers.mainThread())
								.subscribe()
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

	fun observePosts() {
		observeDataDisposable = localDataRepository.observablePosts()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribeOn(Schedulers.io())
			.subscribe(
				{ list ->
					viewModel.setList(list.map { PostListItem(it.id, it.title, it.body) })
					viewModel.setPlaceholderVisible(list.isEmpty())
				}, { t ->
					Timber.e(t)
					viewModel.showError(t)
				}
			)
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
		observeDataDisposable?.dispose()
	}

	fun clear() {
		loadDataDisposable?.dispose()
	}
}