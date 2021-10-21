package com.example.listapp.data.local

import androidx.room.*
import com.example.listapp.data.local.model.PostEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface PostsDao {

	@Query("SELECT * FROM posts")
	fun getAll(): Observable<List<PostEntity>>

	@Query("SELECT * FROM posts WHERE id = :id")
	fun getPost(id: Int): Observable<PostEntity>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAll(vararg post: PostEntity): Completable

	@Delete
	fun delete(post: PostEntity): Completable

	@Query("DELETE FROM posts")
	fun deleteAll(): Completable
}
