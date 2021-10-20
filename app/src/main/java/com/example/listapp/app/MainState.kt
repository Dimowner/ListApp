package com.example.listapp.app

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainState(
	val name: String = "",
	val login: String = "",
	val password: String = "",
	val isNetworkAvailable: Boolean = true
): Parcelable