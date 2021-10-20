package com.example.listapp.logic

import com.example.listapp.logic.model.Post
import io.reactivex.Maybe

interface DataRepository {

	fun getPosts(): Maybe<List<Post>>

	fun getPost(id: Int): Maybe<Post>
}