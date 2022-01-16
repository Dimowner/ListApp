package com.example.listapp.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.listapp.app.posts.PostsListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object PostsModule {

	@Provides
	@FragmentScoped
	fun providePostsListViewModel(
		fragment: Fragment
	): PostsListViewModel {
		return ViewModelProvider(fragment).get(PostsListViewModel::class.java)
	}
}
