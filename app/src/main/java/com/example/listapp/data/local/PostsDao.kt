package com.example.listapp.data.local

import androidx.room.*
import com.example.listapp.data.local.model.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {

	@Query("SELECT * FROM posts")
	fun getAll(): Flow<List<PostEntity>>

	@Query("SELECT * FROM posts WHERE id = :id")
	fun getPost(id: Int): Flow<PostEntity>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertAll(vararg post: PostEntity)

	@Delete
	suspend fun delete(post: PostEntity)

	@Query("DELETE FROM posts")
	suspend fun deleteAll()
}
