package com.example.listapp.data.remote

import com.example.listapp.logic.DataRepository
import com.example.listapp.logic.model.Post
import com.example.listapp.logic.toPost
import io.reactivex.Maybe

class DataRepositoryImpl(
	private val apiPosts: ApiPosts
): DataRepository {

	override fun getPosts(): Maybe<List<Post>> {
		return apiPosts.getPosts().map {
				list -> list.map { it.toPost() }
		}
	}

	override fun getPost(id: Int): Maybe<Post> {
		return apiPosts.getPost(id).map { it.toPost() }
	}
}