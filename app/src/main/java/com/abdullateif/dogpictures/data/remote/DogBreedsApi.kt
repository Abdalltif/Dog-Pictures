package com.abdullateif.dogpictures.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface DogBreedsApi {
    @GET("breeds/list/all/")
    suspend fun fetchDogBreeds(): DogBreedsResponse

    @GET("breed/{breed_name}/images/")
    suspend fun fetchDogBreedImages(
        @Path("breed_name") breedName: String
    ): DogBreedImagesResponse
}