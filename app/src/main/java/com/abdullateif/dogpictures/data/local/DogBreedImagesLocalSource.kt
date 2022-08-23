package com.abdullateif.dogpictures.data.local

import com.abdullateif.dogpictures.data.model.FavoriteImage
import javax.inject.Inject

class DogBreedImagesLocalSource @Inject constructor(
    private val dao: DogBreedImagesDao,
) {

    suspend fun getAllImages(): List<FavoriteImage> {
        return dao.getAllImages()
    }

    suspend fun getAllBreeds(): List<String> {
        return dao.getAllBreeds()
    }

    suspend fun getImagesByBreedName(name: String): List<FavoriteImage> {
        return dao.getImagesByBreedName(name)
    }

    suspend fun insertImage(image: FavoriteImage) {
        dao.insertImage(image)
    }

    suspend fun deleteImage(imgUrl: String) {
        dao.deleteImage(imgUrl)
    }

    suspend fun isImageExist(imgUrl: String) : Boolean {
        return dao.isImageExist(imgUrl)
    }
}