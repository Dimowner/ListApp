package com.example.listapp.app.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.listapp.databinding.ListItemPostBinding

class PostsListAdapter(
	private val onItemClick: (Int) -> Unit
) : ListAdapter<PostListItem, RecyclerView.ViewHolder>(PostsListDiffUtil) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return ItemViewHolder(
			ListItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		) { position ->
			onItemClick.invoke(getItem(position).id)
		}
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val itemHolder = holder as ItemViewHolder
		itemHolder.bind(getItem(position))
	}

	private object PostsListDiffUtil : DiffUtil.ItemCallback<PostListItem>() {
		override fun areItemsTheSame(oldItem: PostListItem, newItem: PostListItem): Boolean {
			return oldItem.id == newItem.id
		}

		override fun areContentsTheSame(oldItem: PostListItem, newItem: PostListItem): Boolean {
			return oldItem == newItem
		}
	}

	private class ItemViewHolder(
		private val binding: ListItemPostBinding,
		onItemClick: (Int) -> Unit
	) : RecyclerView.ViewHolder(binding.root) {

		init {
			binding.root.setOnClickListener {
				onItemClick.invoke(adapterPosition)
			}
		}

		fun bind(item: PostListItem) {
			binding.title.text = item.title
			binding.details.text = item.details
		}
	}
}


