package com.example.listapp.data.remote

import com.example.listapp.data.remote.dto.PostDto
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiPosts {

	@GET("/posts")
	fun getPosts(): Maybe<List<PostDto>>

	@GET("/posts/{id}")
	fun getPost(@Path("id") id: Int): Maybe<PostDto>
}