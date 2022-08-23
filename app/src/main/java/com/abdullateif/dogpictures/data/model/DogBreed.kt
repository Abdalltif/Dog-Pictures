package com.abdullateif.dogpictures.data.model

data class DogBreed(
    val name: String,
    val subBreeds: List<DogSubBreed>
) : BaseModel