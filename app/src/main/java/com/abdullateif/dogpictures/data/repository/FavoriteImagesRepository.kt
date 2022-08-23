package com.abdullateif.dogpictures.data.repository

import com.abdullateif.dogpictures.common.Resource
import com.abdullateif.dogpictures.data.model.FavoriteImage

interface FavoriteImagesRepository {
    suspend fun getAllImages() : Resource<List<FavoriteImage>>
    suspend fun getAllBreeds() : Resource<List<String>>
    suspend fun getImagesByBreedName(name: String) : Resource<List<FavoriteImage>>
    suspend fun saveFavoriteImage(image: FavoriteImage) : String
    suspend fun unFavoriteImage(imgUrl: String)
    suspend fun isImageExistInFavorites(imgUrl: String) : Boolean
}