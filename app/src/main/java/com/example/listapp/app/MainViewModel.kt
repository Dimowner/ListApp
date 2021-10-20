package com.example.listapp.app

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

	private val mutableMainLiveData: MutableLiveData<MainState> = MutableLiveData<MainState>(
		MainState()
	)

	val mainLiveData: LiveData<MainState> = mutableMainLiveData

	fun setName(name: String) {
		mutableMainLiveData.value = mainLiveData.value?.copy(name = name)
	}

	fun setLogin(login: String) {
		mutableMainLiveData.value = mainLiveData.value?.copy(login = login)
	}

	fun setPassword(password: String) {
		mutableMainLiveData.value = mainLiveData.value?.copy(password = password)
	}

	fun setNetworkAvailable(value: Boolean) {
		mutableMainLiveData.value = mainLiveData.value?.copy(isNetworkAvailable = value)
	}

	fun onSaveState(): Parcelable {
		return mainLiveData.value ?: MainState()
	}

	fun onRestoreState(state: MainState) {
		mutableMainLiveData.value = state
	}
}