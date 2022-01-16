package com.example.listapp.app.details

import com.example.listapp.NetworkStateObserver
import com.example.listapp.logic.DataRepository
import com.example.listapp.logic.LocalDataRepository
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FragmentScoped
class DetailsController
@Inject constructor(
	private val viewModel: DetailsViewModel,
	private val networkObserver: NetworkStateObserver,
	private val dataRepository: DataRepository,
	private val localDataRepository: LocalDataRepository
) {

	suspend fun loadPost(id: Int) {
		if (viewModel.isNetworkAvailable()) {
			viewModel.setRefreshing(true)
			withContext(Dispatchers.IO) {
				try {
					val post = dataRepository.getPost(id)
					localDataRepository.insertPosts(listOf(post))
					withContext(Dispatchers.Main) {
						viewModel.setRefreshing(false)
						viewModel.setPlaceholderVisible(false)
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

	suspend fun observePost(id: Int) {
		localDataRepository.observablePost(id)
			.catch {
				Timber.e(it)
				viewModel.showError(it)
			}
			.collect { post ->
				viewModel.setTitle(post.title)
				viewModel.setDetails(post.body)
				viewModel.setPlaceholderVisible(false)
			}
	}

	fun onBackClick() {
		viewModel.navUp()
	}

	suspend fun subscribeNetworkStateChanges() {
		networkObserver.observeNetworkStateChanges()
			.catch { Timber.e(it) }
			.collect { isConnected ->
				viewModel.setPullToRefreshEnabled(isConnected)
				viewModel.setNetworkAvailable(isConnected)
			}
	}
}