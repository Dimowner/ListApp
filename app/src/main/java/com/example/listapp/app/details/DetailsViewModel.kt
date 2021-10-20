package com.example.listapp.app.details

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.listapp.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor() : ViewModel() {

	private val mutableDetailsLiveData: MutableLiveData<DetailsState> = MutableLiveData<DetailsState>(
		DetailsState()
	)

	private val mutableDetailsEventLiveData = SingleLiveEvent<DetailsEvents>()

	val detailsLiveData: LiveData<DetailsState> = mutableDetailsLiveData

	val detailsEventsLiveData: LiveData<DetailsEvents> = mutableDetailsEventLiveData

	fun setTitle(value: String) {
		mutableDetailsLiveData.value = detailsLiveData.value?.copy(title = value)
	}

	fun setDetails(value: String) {
		mutableDetailsLiveData.value = detailsLiveData.value?.copy(details = value)
	}

	fun onSaveState(): Parcelable {
		return mutableDetailsLiveData.value ?: DetailsState()
	}

	fun onRestoreState(state: DetailsState) {
		mutableDetailsLiveData.value = state
	}
}

sealed class DetailsEvents {
}