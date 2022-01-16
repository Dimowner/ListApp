package com.example.listapp.app.posts

import com.example.listapp.NetworkStateObserver
import com.example.listapp.logic.DataRepository
import com.example.listapp.logic.LocalDataRepository
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FragmentScoped
class PostsListController @Inject constructor(
	private val viewModel: PostsListViewModel,
	private val networkObserver: NetworkStateObserver,
	private val dataRepository: DataRepository,
	private val localDataRepository: LocalDataRepository,
) {

	suspend fun loadPosts() {
		if (viewModel.isNetworkAvailable()) {
			viewModel.setRefreshing(true)
			withContext(Dispatchers.IO) {
				try {
					val list = dataRepository.getPosts()
					localDataRepository.insertPosts(list)
					withContext(Dispatchers.Main) {
						viewModel.setRefreshing(false)
						viewModel.setPlaceholderVisible(list.isEmpty())
					}
				} catch (e: Exception) {
					Timber.e(e)
					withContext(Dispatchers.Main) {
						viewModel.showError(e)
						viewModel.setRefreshing(false)
						viewModel.setPlaceholderVisible(true)
					}
				}
			}
		}
	}

	suspend fun observePosts() {
		localDataRepository.observablePosts()
			.catch {
				Timber.e(it)
				viewModel.showError(it)
			}
			.collect { list ->
				viewModel.setList(list.map { PostListItem(it.id, it.title, it.body) })
				viewModel.setPlaceholderVisible(list.isEmpty())
			}
	}

	fun onItemClick(id: Int) {
		viewModel.selectItem(id)
	}

	@FlowPreview
	suspend fun subscribeNetworkStateChanges() {
		networkObserver.observeNetworkStateChanges()
			.catch { Timber.e(it) }
			.collect { isConnected ->
				viewModel.setPullToRefreshEnabled(isConnected)
				viewModel.setNetworkAvailable(isConnected)
			}
	}
}