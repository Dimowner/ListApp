package com.example.listapp.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.listapp.app.posts.PostsListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object PostsModule {

	@Provides
	@ActivityScoped
	fun providePostsListViewModel(
		activity: FragmentActivity
	): PostsListViewModel {
		return ViewModelProvider(activity).get(PostsListViewModel::class.java)
	}
}
