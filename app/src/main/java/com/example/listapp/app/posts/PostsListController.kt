package com.example.listapp.app.posts

import com.example.listapp.NetworkObserver
import dagger.hilt.android.scopes.FragmentScoped
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@FragmentScoped
class PostsListController @Inject constructor(
	private val postsListViewModel: PostsListViewModel,
	private val networkObserver: NetworkObserver
) {

	private var disposable: Disposable? = null

	init {
		val list = listOf(
			PostListItem("1", "title1", "details1"),
			PostListItem("2", "title2", "details2"),
			PostListItem("3", "title3", "details3"),
			PostListItem("4", "title4", "details4"),
			PostListItem("5", "title5", "details5"),
			PostListItem("6", "title6", "details6"),
			PostListItem("7", "title7", "details7"),
			PostListItem("8", "title8", "details8"),
			PostListItem("9", "title9", "details9"),
			PostListItem("10", "title10", "details10")
		)
		postsListViewModel.setList(list)
	}

	fun onItemClick(id: String) {
		postsListViewModel.selectItem(id)
	}

	fun subscribeNetworkStateChanges() {
		disposable?.dispose()
		disposable = networkObserver.observeNetworkStateChanges()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe { isConnected ->
				postsListViewModel.setPullToRefreshEnabled(isConnected)
			}
	}

	fun unsubscribe() {
		disposable?.dispose()
	}
}