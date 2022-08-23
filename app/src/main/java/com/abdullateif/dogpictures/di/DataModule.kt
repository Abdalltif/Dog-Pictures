package com.abdullateif.dogpictures.di

import com.abdullateif.dogpictures.data.local.DogBreedImagesLocalSource
import com.abdullateif.dogpictures.data.remote.DogBreedsApi
import com.abdullateif.dogpictures.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDogBreedsRepository(
        api: DogBreedsApi,
        @DispatcherModule.IoDispatcher ioDispatcher: CoroutineDispatcher
    ): DogBreedsRepository = DogBreedsRepositoryImpl(api, ioDispatcher)

    @Provides
    @Singleton
    fun provideBreedImagesRepository(
        api: DogBreedsApi,
        @DispatcherModule.IoDispatcher ioDispatcher: CoroutineDispatcher
    ): DogBreedImagesRepository = DogBreedImagesRepositoryImpl(api, ioDispatcher)

    @Provides
    @Singleton
    fun provideFavoriteImagesRepository(
        localSource: DogBreedImagesLocalSource,
        @DispatcherModule.IoDispatcher ioDispatcher: CoroutineDispatcher
    ): FavoriteImagesRepository = FavoriteImagesRepositoryImpl(localSource, ioDispatcher)
}