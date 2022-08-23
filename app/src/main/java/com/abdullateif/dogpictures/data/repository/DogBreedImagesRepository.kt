package com.abdullateif.dogpictures.data.repository

import com.abdullateif.dogpictures.common.Resource
import com.abdullateif.dogpictures.data.model.DogBreedImage

interface DogBreedImagesRepository {
    suspend fun fetchDogBreedImages(breedName: String) : Resource<List<DogBreedImage>>
}