package com.example.listapp.logic

import com.example.listapp.data.local.model.PostEntity
import com.example.listapp.data.remote.dto.PostDto
import com.example.listapp.logic.model.Post

fun PostDto.toPost(): Post {
	return Post(
		id = this.id,
		title = this.title,
		body = this.body
	)
}

fun PostEntity.toPost(): Post {
	return Post(
		id = this.id,
		title = this.title,
		body = this.body
	)
}

fun Post.toPostEntity(): PostEntity {
	return PostEntity(
		id = this.id,
		title = this.title,
		body = this.body
	)
}