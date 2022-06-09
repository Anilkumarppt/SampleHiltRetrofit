package com.example.samplehiltretrofit.di

import android.app.Application
import com.example.samplehiltretrofit.data.api.CharacterService
import com.example.samplehiltretrofit.data.remote.CharacterRemoteData
import com.example.samplehiltretrofit.data.repository.CharacterRepository
import com.example.samplehiltretrofit.util.NetworkConstants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val CACHE_SIZE = 5 * 1024 * 1024L // 5 MB

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder().baseUrl(NetworkConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideCharacterService(retrofit: Retrofit): CharacterService =
        retrofit.create(CharacterService::class.java)

    @Provides
    fun provideCharacterRemoteDataSource(charcterService: CharacterService) =
        CharacterRemoteData(charcterService)

    @Provides
    fun provideCharacterRepository(characterRemoteData: CharacterRemoteData) =
        CharacterRepository(characterRemoteData)

    @Singleton
    @Provides
    fun providesOKHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build()
}