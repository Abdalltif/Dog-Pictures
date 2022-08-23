package com.abdullateif.dogpictures.data.repository

import com.abdullateif.dogpictures.common.Resource
import com.abdullateif.dogpictures.data.model.DogBreedImage
import com.abdullateif.dogpictures.data.remote.DogBreedsApi
import com.abdullateif.dogpictures.di.DispatcherModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class DogBreedImagesRepositoryImpl(
    private val dogBreedsApi: DogBreedsApi,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : DogBreedImagesRepository {

    override suspend fun fetchDogBreedImages(breedName: String): Resource<List<DogBreedImage>> {
        return try {
            withContext(ioDispatcher) {

                val dogBreedsResponse = dogBreedsApi.fetchDogBreedImages(breedName)
                val images = dogBreedsResponse.toDogBreedImages()
                Resource.Success(images)

            }
        } catch (exception: HttpException) {
            Resource.Error(exception.localizedMessage ?: "An error occurred!")
        } catch (exception: IOException) {
            Resource.Error(exception.localizedMessage ?: "Network error!")
        }
    }
}