package com.abdullateif.dogpictures.data.local

import com.abdullateif.dogpictures.data.model.FavoriteImage
import javax.inject.Inject

class DogBreedImagesLocalSource @Inject constructor(
    private val dao: DogBreedImagesDao,
) {

    fun getAllImages(): List<FavoriteImage> {
        return dao.getAllImages()
    }

    fun getAllBreeds(): List<String> {
        return dao.getAllBreeds()
    }

    fun getImagesByBreedName(name: String): List<FavoriteImage> {
        return dao.getImagesByBreedName(name)
    }

    fun insertImage(image: FavoriteImage) {
        dao.insertImage(image)
    }

    fun deleteImage(imgUrl: String) {
        dao.deleteImage(imgUrl)
    }

    fun isImageExist(imgUrl: String) : Boolean {
        return dao.isImageExist(imgUrl)
    }
}