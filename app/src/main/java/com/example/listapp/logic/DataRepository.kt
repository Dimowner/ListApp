package com.example.listapp.logic

import com.example.listapp.logic.model.Post

interface DataRepository {

	suspend fun getPosts(): List<Post>

	suspend fun getPost(id: Int): Post
}