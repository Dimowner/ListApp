package com.example.listapp.data.remote

import com.example.listapp.logic.DataRepository
import com.example.listapp.logic.model.Post
import com.example.listapp.logic.toPost

class DataRepositoryImpl(
	private val apiPosts: ApiPosts
): DataRepository {

	override suspend fun getPosts(): List<Post> {
		return apiPosts.getPosts().map { it.toPost() }
	}

	override suspend fun getPost(id: Int): Post {
		return apiPosts.getPost(id).toPost()
	}
}