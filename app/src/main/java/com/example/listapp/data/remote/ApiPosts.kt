package com.example.listapp.data.remote

import com.example.listapp.data.remote.dto.PostDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiPosts {

	@GET("/posts")
	suspend fun getPosts(): List<PostDto>

	@GET("/posts/{id}")
	suspend fun getPost(@Path("id") id: Int): PostDto
}