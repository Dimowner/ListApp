package com.example.listapp.app.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listapp.R
import com.example.listapp.databinding.FragmentPostsListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PostsListFragment: Fragment() {

	private var _binding: FragmentPostsListBinding? = null
	private val binding get() = _binding!!

	@Inject
	lateinit var viewModel: PostsListViewModel

	@Inject
	lateinit var controller: PostsListController

	private lateinit var adapter: PostsListAdapter

	private lateinit var onStartScope: CoroutineScope

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		lifecycleScope.launch {
			controller.loadPosts()
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentPostsListBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.postsListLiveData.observe(viewLifecycleOwner, { updateScreen(it) })
		viewModel.postsListEventsLiveData.observe(viewLifecycleOwner, { onEvent(it) })

		adapter = PostsListAdapter { id ->
			controller.onItemClick(id)
		}
		binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
		binding.recyclerView.addItemDecoration(
			DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
		)
		binding.recyclerView.adapter = adapter

		binding.swipeContainer.setOnRefreshListener {
			lifecycleScope.launch {
				controller.loadPosts()
			}
		}
	}

	override fun onStart() {
		super.onStart()
		onStartScope = CoroutineScope(Dispatchers.Main)
		onStartScope.launch {
			controller.subscribeNetworkStateChanges()
		}
		lifecycleScope.launch {
			controller.observePosts()
		}
	}

	override fun onStop() {
		super.onStop()
		onStartScope.cancel()
	}

	private fun updateScreen(state: PostsListState) {
		if (binding.swipeContainer.isEnabled != state.isPullToRefreshEnabled) {
			binding.swipeContainer.isEnabled = state.isPullToRefreshEnabled
		}
		if (binding.swipeContainer.isRefreshing != state.isRefreshing) {
			binding.swipeContainer.isRefreshing = state.isRefreshing
		}
		binding.placeholder.isVisible = state.isPlaceholderVisible
		adapter.submitList(state.listData)
	}

	private fun onEvent(event: PostsListEvents) {
		when (event) {
			is PostsListEvents.OpenDetails -> {
				findNavController().navigate(
					PostsListFragmentDirections.toDetails(id = event.id)
				)
			}
			is PostsListEvents.ShowError -> {
				Toast.makeText(requireContext(), R.string.error_failed_to_load, Toast.LENGTH_LONG).show()
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		outState.putParcelable(this::class.java.simpleName, viewModel.onSaveState())
	}

	override fun onViewStateRestored(savedInstanceState: Bundle?) {
		super.onViewStateRestored(savedInstanceState)
		savedInstanceState?.getParcelable<PostsListState>(this::class.java.simpleName)?.let {
			viewModel.onRestoreState(it)
		}
	}
}
