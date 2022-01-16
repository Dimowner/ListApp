package com.example.listapp.app.details

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.listapp.util.SingleLiveEvent

class DetailsViewModel : ViewModel() {

	private val mutableDetailsLiveData: MutableLiveData<DetailsState> = MutableLiveData<DetailsState>(
		DetailsState()
	)

	private val mutableDetailsEventLiveData = SingleLiveEvent<DetailsEvents>()

	val detailsLiveData: LiveData<DetailsState> = mutableDetailsLiveData

	val detailsEventsLiveData: LiveData<DetailsEvents> = mutableDetailsEventLiveData

	fun setNetworkAvailable(value: Boolean) {
		mutableDetailsLiveData.value = detailsLiveData.value?.copy(isNetworkAvailable = value)
	}

	fun isNetworkAvailable(): Boolean {
		return detailsLiveData.value?.isNetworkAvailable ?: false
	}

	fun setPullToRefreshEnabled(value: Boolean) {
		mutableDetailsLiveData.value = detailsLiveData.value?.copy(isPullToRefreshEnabled = value)
	}

	fun setRefreshing(value: Boolean) {
		mutableDetailsLiveData.value = detailsLiveData.value?.copy(isRefreshing = value)
	}

	fun setPlaceholderVisible(value: Boolean) {
		mutableDetailsLiveData.value = detailsLiveData.value?.copy(isPlaceholderVisible = value)
	}

	fun setTitle(value: String) {
		mutableDetailsLiveData.value = detailsLiveData.value?.copy(title = value)
	}

	fun setDetails(value: String) {
		mutableDetailsLiveData.value = detailsLiveData.value?.copy(details = value)
	}

	fun showError(t: Throwable) {
		mutableDetailsEventLiveData.value = DetailsEvents.ShowError(t)
	}

	fun navUp() {
		mutableDetailsEventLiveData.value = DetailsEvents.NavUp
	}

	fun onSaveState(): Parcelable {
		return mutableDetailsLiveData.value ?: DetailsState()
	}

	fun onRestoreState(state: DetailsState) {
		mutableDetailsLiveData.value = state
	}
}

sealed class DetailsEvents {
	data class ShowError(val t: Throwable): DetailsEvents()
	object NavUp: DetailsEvents()
}