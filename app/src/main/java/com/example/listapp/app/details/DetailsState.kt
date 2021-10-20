package com.example.listapp.app.details

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailsState(
	val isNetworkAvailable: Boolean = true,
	val isPullToRefreshEnabled: Boolean = true,
	val isRefreshing: Boolean = false,
	val isPlaceholderVisible: Boolean = false,
	val title: String = "",
	val details: String = "",
): Parcelable
