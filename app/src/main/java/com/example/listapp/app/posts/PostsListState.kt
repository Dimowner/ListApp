package com.example.listapp.app.posts

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostsListState(
	val isNetworkAvailable: Boolean = true,
	val isPullToRefreshEnabled: Boolean = true,
	val isRefreshing: Boolean = false,
	val isPlaceholderVisible: Boolean = false,
	val listData: List<PostListItem> = emptyList()
): Parcelable
