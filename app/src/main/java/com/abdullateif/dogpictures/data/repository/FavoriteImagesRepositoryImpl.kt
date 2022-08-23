package com.abdullateif.dogpictures.data.repository

import com.abdullateif.dogpictures.common.Resource
import com.abdullateif.dogpictures.data.local.DogBreedImagesLocalSource
import com.abdullateif.dogpictures.data.model.FavoriteImage
import com.abdullateif.dogpictures.di.DispatcherModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteImagesRepositoryImpl @Inject constructor(
    private val favImagesLocalSource: DogBreedImagesLocalSource,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FavoriteImagesRepository {
    override suspend fun getAllImages(): Resource<List<FavoriteImage>> {
        return try {
            withContext(ioDispatcher) {

                val images = favImagesLocalSource.getAllImages()
                Resource.Success(images)

            }
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage ?: "An error occurred!")
        }
    }

    override suspend fun getAllBreeds(): Resource<List<String>> {
        return try {
            withContext(ioDispatcher) {

                val breeds = favImagesLocalSource.getAllBreeds()
                Resource.Success(breeds)

            }
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage ?: "An error occurred!")
        }
    }

    override suspend fun getImagesByBreedName(name: String): Resource<List<FavoriteImage>> {
        return try {
            withContext(ioDispatcher) {

                val images = favImagesLocalSource.getImagesByBreedName(name)
                Resource.Success(images)

            }
        } catch (exception: Exception) {
            Resource.Error(exception.localizedMessage ?: "An error occurred!")
        }
    }

    override suspend fun saveFavoriteImage(image: FavoriteImage) : String {
        return if (isImageExistInFavorites(image.imgUrl)) {
            "Already liked"
        } else {
            favImagesLocalSource.insertImage(image)
            "Liked"
        }
    }

    override suspend fun unFavoriteImage(imgUrl: String) {
        favImagesLocalSource.deleteImage(imgUrl)
    }

    override suspend fun isImageExistInFavorites(imgUrl: String): Boolean {
        return favImagesLocalSource.isImageExist(imgUrl)
    }
}