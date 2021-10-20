package com.example.listapp.app.posts

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class PostListItem(
	val id: Int,
	val title: String,
	val details: String,
	val date: Calendar? = null
): Parcelable