package com.example.listapp.di

import android.content.Context
import androidx.room.Room
import com.example.listapp.data.local.AppDatabase
import com.example.listapp.data.local.LocalDataRepositoryImpl
import com.example.listapp.data.remote.ApiPosts
import com.example.listapp.logic.DataRepository
import com.example.listapp.data.remote.DataRepositoryImpl
import com.example.listapp.logic.LocalDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

	@Provides
	@Singleton
	fun provideLocalDataRepository(
		appDatabase: AppDatabase
	): LocalDataRepository {
		return LocalDataRepositoryImpl(appDatabase)
	}

	@Provides
	@Singleton
	fun provideAppDatabase(
		@ApplicationContext context: Context
	): AppDatabase {
		return Room.databaseBuilder(context, AppDatabase::class.java, "app-database").build()
	}
}
