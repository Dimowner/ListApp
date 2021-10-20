package com.example.listapp.di

import com.example.listapp.data.ApiPosts
import com.example.listapp.logic.DataRepository
import com.example.listapp.data.DataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

	@Provides
	@Singleton
	fun provideDataRepository(
		apiPosts: ApiPosts
	): DataRepository {
		return DataRepositoryImpl(apiPosts)
	}
}
