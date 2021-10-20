package com.example.listapp.app.posts

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.listapp.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostsListViewModel @Inject constructor() : ViewModel() {

	private val mutablePostsListLiveData: MutableLiveData<PostsListState> = MutableLiveData<PostsListState>(
		PostsListState()
	)

	private val mutableListEventsLiveData = SingleLiveEvent<PostsListEvents>()

	val postsListLiveData: LiveData<PostsListState> = mutablePostsListLiveData

	val postsListEventsLiveData: LiveData<PostsListEvents> = mutableListEventsLiveData

	fun setNetworkAvailable(value: Boolean) {
		mutablePostsListLiveData.value = postsListLiveData.value?.copy(isNetworkAvailable = value)
	}

	fun isNetworkAvailable(): Boolean {
		return postsListLiveData.value?.isNetworkAvailable ?: false
	}

	fun setPullToRefreshEnabled(value: Boolean) {
		mutablePostsListLiveData.value = postsListLiveData.value?.copy(isPullToRefreshEnabled = value)
	}

	fun setRefreshing(value: Boolean) {
		mutablePostsListLiveData.value = postsListLiveData.value?.copy(isRefreshing = value)
	}

	fun setPlaceholderVisible(value: Boolean) {
		mutablePostsListLiveData.value = postsListLiveData.value?.copy(isPlaceholderVisible = value)
	}

	fun setList(list: List<PostListItem>) {
		mutablePostsListLiveData.value = postsListLiveData.value?.copy(listData = list)
	}

	fun selectItem(id: Int) {
		mutableListEventsLiveData.value = PostsListEvents.OpenDetails(id)
	}

	fun showError(t: Throwable) {
		mutableListEventsLiveData.value = PostsListEvents.ShowError(t)
	}

	fun onSaveState(): Parcelable {
		return mutablePostsListLiveData.value ?: PostsListState()
	}

	fun onRestoreState(state: PostsListState) {
		mutablePostsListLiveData.value = state
	}
}

sealed class PostsListEvents {
	data class OpenDetails(val id: Int): PostsListEvents()
	data class ShowError(val t: Throwable): PostsListEvents()
}