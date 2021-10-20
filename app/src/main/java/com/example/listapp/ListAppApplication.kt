package com.example.listapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ListAppApplication : Application() {

	override fun onCreate() {
		super.onCreate()

		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

		if (BuildConfig.DEBUG) {
			//Timber initialization
			Timber.plant(object : Timber.DebugTree() {
				override fun createStackElementTag(element: StackTraceElement): String {
					return super.createStackElementTag(element) + ":" + element.lineNumber
				}
			})
		}
	}

	override fun onLowMemory() {
		super.onLowMemory()
		Timber.d("onLowMemory")
	}

	override fun onTrimMemory(level: Int) {
		super.onTrimMemory(level)
		Timber.d("onTrimMemory level = %s", level)
	}
}
