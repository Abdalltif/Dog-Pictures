package com.abdullateif.dogpictures.core

import com.abdullateif.dogpictures.data.model.DogBreed
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