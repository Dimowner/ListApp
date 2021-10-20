package com.example.listapp.app.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.listapp.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment: Fragment() {

	@Inject
	lateinit var controller: DetailsController

	//TODO: Fix activity scoped viewModel
	private val viewModel: DetailsViewModel by activityViewModels()

	private val args: DetailsFragmentArgs by navArgs()

	private var _binding: FragmentDetailsBinding? = null
	private val binding get() = _binding!!

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		controller.init(args.id)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentDetailsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewModel.detailsLiveData.observe(viewLifecycleOwner, { updateScreen(it) })
		viewModel.detailsEventsLiveData.observe(viewLifecycleOwner, { onEvent(it) })
	}

	override fun onStart() {
		super.onStart()
		controller.subscribeNetworkStateChanges()
	}

	override fun onStop() {
		super.onStop()
		controller.unsubscribe()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun updateScreen(state: DetailsState) {
		binding.contentText.text = state.title
	}

	private fun onEvent(event: DetailsEvents) {
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		outState.putParcelable(this::class.java.simpleName, viewModel.onSaveState())
	}

	override fun onViewStateRestored(savedInstanceState: Bundle?) {
		super.onViewStateRestored(savedInstanceState)
		savedInstanceState?.getParcelable<DetailsState>(this::class.java.simpleName)?.let {
			viewModel.onRestoreState(it)
		}
	}
}