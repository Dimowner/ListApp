package com.example.listapp.logic

import com.example.listapp.logic.model.Post
import kotlinx.coroutines.flow.Flow

interface LocalDataRepository {

	suspend fun insertPosts(list: List<Post>)

	fun observablePosts(): Flow<List<Post>>

	fun observablePost(id: Int): Flow<Post>
}