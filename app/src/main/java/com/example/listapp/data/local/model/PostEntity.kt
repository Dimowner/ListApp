package com.example.listapp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
	@PrimaryKey val id: Int,
	@ColumnInfo(name = "title") val title: String,
	@ColumnInfo(name = "body") val body: String
)
