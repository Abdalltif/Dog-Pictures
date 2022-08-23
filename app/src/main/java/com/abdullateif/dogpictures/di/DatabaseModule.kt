package com.abdullateif.dogpictures.di

import android.content.Context
import com.abdullateif.dogpictures.data.local.DogBreedImagesDao
import com.abdullateif.dogpictures.data.local.DogBreedImagesDatabase
import com.abdullateif.dogpictures.data.local.DogBreedImagesLocalSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providerDogBreedImagesDB(@ApplicationContext context: Context): DogBreedImagesDatabase {
        return DogBreedImagesDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideFavDao(favoritesDatabase: DogBreedImagesDatabase): DogBreedImagesDao {
        return favoritesDatabase.dogBreedImagesDao()
    }

    @Singleton
    @Provides
    fun provideLocalSource(dogBreedImagesDao: DogBreedImagesDao): DogBreedImagesLocalSource {
        return DogBreedImagesLocalSource(dogBreedImagesDao)
    }

}