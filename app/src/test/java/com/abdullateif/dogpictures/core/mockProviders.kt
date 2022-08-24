package com.abdullateif.dogpictures.core

import com.abdullateif.dogpictures.data.model.DogBreed
import com.abdullateif.dogpictures.data.model.DogBreedImage
import com.abdullateif.dogpictures.data.model.FavoriteImage
import com.abdullateif.dogpictures.data.remote.DogBreedImagesResponse
import com.abdullateif.dogpictures.data.remote.DogBreedsResponse
import io.mockk.every
import io.mockk.mockk

fun dogBreedsResponseMockProvider() = mockk<DogBreedsResponse> {
    every { status } returns "success"
    every { message } returns mapProvider()
}

private fun getMockDogBreed(id: Int = 2) =
    DogBreed(
        name = "Name $id",
        subBreeds = emptyList()
    )

fun breedListMockProvider() =
    (1..5).map {
        getMockDogBreed(id = it)
    }

fun mapProvider() = mapOf<String, List<String>>("african" to emptyList(), "bulldog" to listOf("bullone", "bulltwo"))

fun dogBreedImagesResponseMockProvider() = mockk<DogBreedImagesResponse> {
    every { status } returns "success"
    every { message } returns listOf("url1", "url2", "url3")
}

private fun getMockDogBreedImage(id: Int = 2) =
    DogBreedImage(
        imageUrl = "url $id",
    )

fun breedImagesListMockProvider() =
    (1..10).map {
        getMockDogBreedImage(id = it)
    }

private fun getMockFavoriteImage(id: Int = 2) =
    FavoriteImage(
        id = id,
        imgUrl = "url $id",
        breedName = "name $id"
    )

fun favoriteImagesListMockProvider() =
    (1..10).map {
        getMockFavoriteImage(id = it)
    }