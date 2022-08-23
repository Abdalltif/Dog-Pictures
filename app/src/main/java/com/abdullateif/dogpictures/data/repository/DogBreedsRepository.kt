package com.abdullateif.dogpictures.data.repository

import com.abdullateif.dogpictures.common.Resource
import com.abdullateif.dogpictures.data.model.DogBreed

interface DogBreedsRepository {
    suspend fun fetchDogBreeds() : Resource<List<DogBreed>>
}