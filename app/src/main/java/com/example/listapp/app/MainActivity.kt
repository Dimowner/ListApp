package com.example.listapp.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.listapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	private val mainViewModel: MainViewModel by viewModels()

	private lateinit var binding: ActivityMainBinding

	@Inject
	lateinit var controller: MainController

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		mainViewModel.mainLiveData.observe(this, { updateScreen(it) })
	}

	override fun onStart() {
		super.onStart()
		controller.subscribeNetworkStateChanges()
	}

	override fun onStop() {
		super.onStop()
		controller.unsubscribe()
	}

	private fun updateScreen(state: MainState) {
		binding.networkNotConnected.isVisible = !state.isNetworkAvailable
	}

	override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
		super.onSaveInstanceState(outState, outPersistentState)
		outState.putParcelable(this::class.java.simpleName, mainViewModel.onSaveState())
	}

	override fun onRestoreInstanceState(savedInstanceState: Bundle) {
		super.onRestoreInstanceState(savedInstanceState)
		savedInstanceState.getParcelable<MainState>(this::class.java.simpleName)?.let {
			mainViewModel.onRestoreState(it)
		}
	}
}