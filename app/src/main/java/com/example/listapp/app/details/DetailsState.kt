package com.example.listapp.app.details

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailsState(
	val title: String = "",
	val details: String = "",
): Parcelable
