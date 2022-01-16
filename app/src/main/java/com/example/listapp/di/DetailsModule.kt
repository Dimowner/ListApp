package com.example.listapp.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.listapp.app.details.DetailsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object DetailsModule {

	@Provides
	@FragmentScoped
	fun provideDetailsViewModel(
		fragment: Fragment
	): DetailsViewModel {
		val viewModel =  ViewModelProvider(fragment).get(DetailsViewModel::class.java)
		return viewModel
	}
}