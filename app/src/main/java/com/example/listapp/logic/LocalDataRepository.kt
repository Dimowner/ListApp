package com.example.listapp.logic

import com.example.listapp.logic.model.Post
import io.reactivex.Completable
import io.reactivex.Observable

interface LocalDataRepository {

	fun insertPosts(list: List<Post>): Completable

	fun observablePosts(): Observable<List<Post>>

	fun observablePost(id: Int): Observable<Post>
}