package com.example.listapp.logic

import com.example.listapp.data.dto.PostDto
import com.example.listapp.logic.model.Post

fun PostDto.toPost(): Post {
	return Post(
		id = this.id,
		title = this.title,
		body = this.body
	)
}