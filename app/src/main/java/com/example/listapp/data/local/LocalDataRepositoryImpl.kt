package com.example.listapp.data.local

import com.example.listapp.logic.LocalDataRepository
import com.example.listapp.logic.model.Post
import com.example.listapp.logic.toPost
import com.example.listapp.logic.toPostEntity
import io.reactivex.Completable
import io.reactivex.Observable

class LocalDataRepositoryImpl(
	private val appDatabase: AppDatabase
): LocalDataRepository {

	override fun insertPosts(list: List<Post>): Completable {
		return appDatabase.postDao()
			.insertAll(*list.map { it.toPostEntity() }.toTypedArray())
	}

	override fun observablePosts(): Observable<List<Post>> {
		return appDatabase.postDao().getAll().map {
			it.map { post -> post.toPost() }
		}
	}

	override fun observablePost(id: Int): Observable<Post> {
		return appDatabase.postDao().getPost(id).map { it.toPost() }
	}
}