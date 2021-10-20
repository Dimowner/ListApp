package com.example.listapp.app

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainState(
	val isNetworkAvailable: Boolean = true
): Parcelable