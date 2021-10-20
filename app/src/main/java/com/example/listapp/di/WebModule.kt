package com.example.listapp.di

import com.example.listapp.BuildConfig
import com.example.listapp.data.ApiPosts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val TIMEOUT = 10000L //mills
private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

@Module
@InstallIn(SingletonComponent::class)
object WebModule {

	@Provides
	@Singleton
	fun provideApi(
		okHttpClient: OkHttpClient
	): ApiPosts {
		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create())
			.client(okHttpClient)
			.build()
			.create(ApiPosts::class.java)
	}

	@Provides
	fun createBaseHttpClient(): OkHttpClient {
		val builder = OkHttpClient.Builder()
			.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
			.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
			.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
		if (BuildConfig.DEBUG) {
			builder.addInterceptor(HttpLoggingInterceptor())
		}
		return builder.build()
	}
}