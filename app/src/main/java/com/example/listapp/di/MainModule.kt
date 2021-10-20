package com.example.listapp.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.listapp.app.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object MainModule {

	@Provides
	@ActivityScoped
	fun provideMainViewModel(
		activity: FragmentActivity
	): MainViewModel {
		return ViewModelProvider(activity).get(MainViewModel::class.java)
	}
}
