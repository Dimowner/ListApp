package com.example.listapp.data.local

import com.example.listapp.logic.LocalDataRepository
import com.example.listapp.logic.model.Post
import com.example.listapp.logic.toPost
import com.example.listapp.logic.toPostEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataRepositoryImpl(
	private val appDatabase: AppDatabase
): LocalDataRepository {

	override suspend fun insertPosts(list: List<Post>) {
		return appDatabase.postDao()
			.insertAll(*list.map { it.toPostEntity() }.toTypedArray())
	}

	override fun observablePosts(): Flow<List<Post>> {
		return appDatabase.postDao().getAll().map { list -> list.map { it.toPost() }}
	}

	override fun observablePost(id: Int): Flow<Post> {
		return appDatabase.postDao().getPost(id).map { it.toPost() }
	}
}