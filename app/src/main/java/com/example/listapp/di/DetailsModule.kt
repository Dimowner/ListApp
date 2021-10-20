package com.example.listapp.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.listapp.app.details.DetailsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object DetailsModule {

	@Provides
	@ActivityScoped
	fun provideDetailsViewModel(
		activity: FragmentActivity
	): DetailsViewModel {
		return ViewModelProvider(activity).get(DetailsViewModel::class.java)
	}
}