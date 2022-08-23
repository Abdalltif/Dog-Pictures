package com.abdullateif.dogpictures.data.repository

import com.abdullateif.dogpictures.common.Resource
import com.abdullateif.dogpictures.data.model.DogBreed
import com.abdullateif.dogpictures.data.remote.DogBreedsApi
import com.abdullateif.dogpictures.di.DispatcherModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class DogBreedsRepositoryImpl(
    private val dogBreedsApi: DogBreedsApi,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : DogBreedsRepository {

    override suspend fun fetchDogBreeds(): Resource<List<DogBreed>> {
        return try {
            withContext(ioDispatcher) {

                val dogBreedsResponse = dogBreedsApi.fetchDogBreeds()
                val breeds = dogBreedsResponse.toDogBreeds()
                Resource.Success(breeds)

            }
        } catch (exception: HttpException) {
            Resource.Error(exception.localizedMessage ?: "An error occurred!")
        } catch (exception: IOException) {
            Resource.Error(exception.localizedMessage ?: "Network error!")
        }
    }
}